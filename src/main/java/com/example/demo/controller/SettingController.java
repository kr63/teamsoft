package com.example.demo.controller;

import com.example.demo.dto.SettingDto;
import com.example.demo.entity.Setting;
import com.example.demo.entity.SettingMapper;
import com.example.demo.exception.SettingNotFoundException;
import com.example.demo.service.DetailService;
import com.example.demo.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class SettingController {

    private final SettingMapper settingMapper;
    private final SettingService settingService;
    private final DetailService detailService;

    public SettingController(SettingMapper settingMapper, SettingService settingService, DetailService detailService) {
        this.settingMapper = settingMapper;
        this.settingService = settingService;
        this.detailService = detailService;
    }

    @GetMapping("/setting")
    public ResponseEntity<Iterable<Setting>> getAll() {
        return new ResponseEntity<>(settingService.getAllSettings(), HttpStatus.OK);
    }

    @GetMapping("/setting/{id}")
    public ResponseEntity<Setting> getSetting(@PathVariable Long id) {
        return settingService.getSettingById(id)
                .map(setting -> new ResponseEntity<>(setting, HttpStatus.OK))
                .orElseThrow(() -> new SettingNotFoundException("Not found setting with id: " + id));
    }

    @PostMapping("setting")
    public ResponseEntity<Setting> addSetting(@Validated @RequestBody Setting setting) {
        setting.setId(null);
        settingService.saveSetting(setting);
        return new ResponseEntity<>(setting, HttpStatus.CREATED);
    }

    @DeleteMapping("/setting/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        Optional<Setting> setting = settingService.getSettingById(id);
        setting.orElseThrow(() -> new SettingNotFoundException("Not found setting with id: " + id));
        settingService.deleteSettingById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @DeleteMapping("/detail/{id}")
    public ResponseEntity<Void> deleteDetail(@PathVariable Long id) {
        detailService.deleteDetailById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @PatchMapping("/setting/{id}")
    public ResponseEntity<Setting> updateSetting(
            @PathVariable Long id, @Validated @RequestBody SettingDto requestDto) {

        Setting setting = settingService.getSettingById(id)
                .orElseThrow(() -> new SettingNotFoundException("Not found setting wiht id: " + id));
        /*
        db & request collections exist --> clear db
        request collection null --> ignored
         */
        try {
            if (!setting.getDetails().isEmpty() && !requestDto.getDetails().isEmpty()) {
                setting.getDetails().forEach(detailService::deleteDetail);
            }
        } catch (NullPointerException ignored) {
        }

        /*
        request collection empty --> clear db
        request collection null --> ignored
         */
        try {
            if (requestDto.getDetails().isEmpty()) {
                setting.getDetails().forEach(detailService::deleteDetail);
            }
        } catch (NullPointerException ignored) {
        }

        // map request --> entity from db & update backreference
        settingMapper.dtoToSetting(requestDto, setting);
        setting.getDetails().forEach((detail) -> detail.setSetting(setting));

        settingService.saveSetting(setting);
        return new ResponseEntity<>(setting, HttpStatus.OK);
    }
}

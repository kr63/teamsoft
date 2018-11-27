package com.example.demo.controller;

import com.example.demo.dto.SettingDto;
import com.example.demo.entity.Setting;
import com.example.demo.entity.SettingMapper;
import com.example.demo.exception.SettingNotFoundException;
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

    public SettingController(SettingMapper settingMapper, SettingService settingService) {
        this.settingMapper = settingMapper;
        this.settingService = settingService;
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

    @PatchMapping("/setting/{id}")
    public ResponseEntity<Setting> updateSetting(
            @PathVariable Long id, @Validated @RequestBody SettingDto dto) {

        Optional<Setting> setting = settingService.getSettingById(id);
        setting.orElseThrow(() -> new SettingNotFoundException("Not found setting with id: " + id));
        settingMapper.mapToSetting(dto, setting.get());
        settingService.saveSetting(setting.get());
        return new ResponseEntity<>(setting.get(), HttpStatus.OK);
    }
}

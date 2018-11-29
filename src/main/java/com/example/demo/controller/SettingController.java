package com.example.demo.controller;

import com.example.demo.dto.SettingDto;
import com.example.demo.entity.Setting;
import com.example.demo.entity.SettingMapper;
import com.example.demo.exception.SettingNotFoundException;
import com.example.demo.service.DetailService;
import com.example.demo.service.SettingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "Return all setting values")
    @GetMapping("/setting")
    public ResponseEntity<Iterable<Setting>> getAll() {
        return new ResponseEntity<>(settingService.getAllSettings(), HttpStatus.OK);
    }

    @ApiOperation(value = "Return specific setting by id")
    @GetMapping("/setting/{id}")
    public ResponseEntity<Setting> getSetting(
            @ApiParam(value = "The id of the required setting", required = true)
            @PathVariable Long id) {
        return settingService.getSettingById(id)
                .map(setting -> new ResponseEntity<>(setting, HttpStatus.OK))
                .orElseThrow(() -> new SettingNotFoundException("Not found setting with id: " + id));
    }

    @ApiOperation(value = "Create setting object")
    @PostMapping("setting")
    public ResponseEntity<Setting> addSetting(@Validated @RequestBody Setting setting) {
        setting.setId(null);
        settingService.saveSetting(setting);
        return new ResponseEntity<>(setting, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete specific setting entity by id")
    @DeleteMapping("/setting/{id}")
    public ResponseEntity<Void> deleteSetting(
            @ApiParam(value = "The id of the setting to delete", required = true)
            @PathVariable Long id) {
        Optional<Setting> setting = settingService.getSettingById(id);
        setting.orElseThrow(() -> new SettingNotFoundException("Not found setting with id: " + id));
        settingService.deleteSettingById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @ApiOperation(value = "Delete specific details entity by id")
    @DeleteMapping("/detail/{id}")
    public ResponseEntity<Void> deleteDetail(
            @ApiParam(value = "The id of the entity to delete", required = true)
            @PathVariable Long id) {
        detailService.deleteDetailById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @ApiOperation(value = "Update specific setting entity by id")
    @PatchMapping("/setting/{id}")
    public ResponseEntity<Setting> updateSetting(
            @ApiParam(value = "The id of the entity to update", required = true)
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

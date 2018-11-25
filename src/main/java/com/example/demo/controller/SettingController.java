package com.example.demo.controller;

import com.example.demo.exception.SettingNotFoundException;
import com.example.demo.model.Setting;
import com.example.demo.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping(value = "/setting")
    public ResponseEntity<Iterable<Setting>> getAll() {
        return new ResponseEntity<>(settingService.getAllSettings(), HttpStatus.OK);
    }

    @GetMapping(value = "/setting/{id}")
    public ResponseEntity<Setting> getSettingById(@PathVariable Long id) {
        return settingService.getSetting(id)
                .map(setting -> new ResponseEntity<>(setting, HttpStatus.OK))
                .orElseThrow(() -> new SettingNotFoundException("Not found setting with id: " + id));
    }
}

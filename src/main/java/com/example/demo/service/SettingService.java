package com.example.demo.service;

import com.example.demo.model.Setting;

import java.util.Optional;

public interface SettingService {
    Iterable<Setting> getAllSettings();

    Optional<Setting> getSettingById(Long id);

    void saveSetting(Setting setting);

    void deleteSettingById(Long id);
}

package com.example.demo.service;

import com.example.demo.model.Setting;

import java.util.Optional;

public interface SettingService {
    Iterable<Setting> getAllSettings();

    Optional<Setting> getSetting(Long id);

    void saveSetting(Setting setting);

    void delete(Long id);
}

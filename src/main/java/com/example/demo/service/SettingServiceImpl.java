package com.example.demo.service;

import com.example.demo.Repository.SettingRepository;
import com.example.demo.model.Setting;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    public SettingServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public Iterable<Setting> getAllSettings() {
        return settingRepository.findAll();
    }

    @Override
    public Optional<Setting> getSetting(Long id) {
        return settingRepository.findById(id);
    }

    @Override
    public Setting saveSetting(Setting setting) {
        return settingRepository.save(setting);
    }
}

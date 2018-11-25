package com.example.demo.service;

import com.example.demo.Repository.SettingRepository;
import com.example.demo.model.Setting;
import org.springframework.stereotype.Service;

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
}

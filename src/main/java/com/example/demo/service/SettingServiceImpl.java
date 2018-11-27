package com.example.demo.service;

import com.example.demo.Repository.SettingRepository;
import com.example.demo.entity.Setting;
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
    public Optional<Setting> getSettingById(Long id) {
        return settingRepository.findById(id);
    }

    @Override
    public void saveSetting(Setting setting) {
        settingRepository.save(setting);
    }

    @Override
    public void deleteSettingById(Long id) {
        settingRepository.deleteById(id);
    }
}

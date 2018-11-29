package com.example.demo.repository;

import com.example.demo.entity.Setting;
import org.springframework.data.repository.CrudRepository;

public interface SettingRepository extends CrudRepository<Setting, Long> {
}

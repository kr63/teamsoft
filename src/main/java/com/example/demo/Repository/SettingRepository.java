package com.example.demo.Repository;

import com.example.demo.model.Setting;
import org.springframework.data.repository.CrudRepository;

public interface SettingRepository extends CrudRepository<Setting, Long> {
}

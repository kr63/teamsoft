package com.example.demo.entity;

import com.example.demo.dto.SettingDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SettingMapper {

    SettingMapper SETTING_MAPPER = Mappers.getMapper(SettingMapper.class);

    SettingDto convertToDto(Setting setting);

    Setting convertFromDto(SettingDto dto);
}

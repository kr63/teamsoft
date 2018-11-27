package com.example.demo.entity;

import com.example.demo.dto.SettingDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface SettingMapper {

    SettingMapper INSTANCE = Mappers.getMapper(SettingMapper.class);

    void mapToSetting(SettingDto dto, @MappingTarget Setting setting);

    void mapToDto(Setting setting, @MappingTarget SettingDto dto);

    Setting convertFromDto(SettingDto dto);

    SettingDto convertToDto(Setting setting);
}

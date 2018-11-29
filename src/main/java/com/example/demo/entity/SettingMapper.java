package com.example.demo.entity;

import com.example.demo.dto.DetailDto;
import com.example.demo.dto.SettingDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface SettingMapper {

    SettingMapper INSTANCE = Mappers.getMapper(SettingMapper.class);

    @Mapping(target = "id", ignore = true)
    void dtoToSetting(SettingDto dto, @MappingTarget Setting setting);

    void settingToDto(Setting setting, @MappingTarget SettingDto dto);

    Setting dtoToSetting(SettingDto dto);

    Detail dtoToDetail(DetailDto dto);

    SettingDto settingToDto(Setting setting);

    DetailDto detailToDto(Detail detail);

    List<Detail> dtoToDetails(List<DetailDto> dto);

    List<DetailDto> detailsToDto(List<Detail> dto);
}

package com.example.demo.entity;

import com.example.demo.dto.SettingDto;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import static junit.framework.TestCase.assertNotNull;

public class SettingMapperTest {

    @Test
    public void shouldMapSettingToDto() {
        // given
        Setting setting = new Setting(1L, "type1", 1, new ArrayList<>());

        OffsetDateTime time = OffsetDateTime.now();
        Detail detail1 = new Detail(1L, time, setting);
        Detail detail2 = new Detail(2L, time, setting);
        setting.details.add(detail1);
        setting.details.add(detail2);

        // when
        SettingDto dto = SettingMapper.INSTANSE.convertToDto(setting);

        // than
        assertNotNull(dto);
    }
}
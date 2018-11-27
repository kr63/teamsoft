package com.example.demo.entity;

import com.example.demo.dto.SettingDto;
import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class SettingMapperTest {

    private Setting setting;
    private SettingDto dto;
    private Detail detail1;
    private Detail detail2;

    @Before
    public void setUp() {
        setting = new Setting();
        dto = new SettingDto();
        OffsetDateTime time = OffsetDateTime.now();
        detail1 = new Detail(1L, time, setting);
        detail2 = new Detail(2L, time, setting);
        setting.details = new ArrayList<>();
        setting.details.addAll(Arrays.asList(detail1, detail2));
        dto.setDetails(new ArrayList<>());
    }

    @Test
    public void shouldMapSettingToDto() {
        // given
        setting.setId(1L);
        setting.setType("type1");
        setting.setItem1(1);

        // when
        dto = SettingMapper.INSTANCE.convertToDto(setting);

        // than
        assertNotNull(dto);
        assertEquals("type1", dto.getType());
        assertEquals(detail1, dto.getDetails().get(0));
        assertEquals(detail2, dto.getDetails().get(1));
    }

    @Test
    public void shouldMapDtoToSetting() {
        // given
        setting.details = new ArrayList<>();
        dto.getDetails().addAll(Arrays.asList(detail1, detail2));
        dto.setType("type1");
        dto.setId(1L);

        // when
        setting = SettingMapper.INSTANCE.convertFromDto(dto);

        // than
        assertNotNull(setting);
        assertEquals("type1", setting.getType());
        assertEquals(detail1, setting.getDetails().get(0));
        assertEquals(detail2, setting.getDetails().get(1));
    }
}
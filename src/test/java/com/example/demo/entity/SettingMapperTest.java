package com.example.demo.entity;

import com.example.demo.dto.DetailDto;
import com.example.demo.dto.SettingDto;
import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.demo.entity.SettingMapper.INSTANCE;
import static junit.framework.TestCase.assertNotNull;

public class SettingMapperTest {

    private Setting setting;
    private SettingDto dto;
    private Detail detail1;
    private Detail detail2;
    private DetailDto detailDto1;
    private DetailDto detailDto2;

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

//    @Test
//    public void shouldMapSettingToDto() {
//        // given
//        setting.setId(1L);
//        setting.setType("type1");
//        setting.setItem1(1);
//
//        // when
//        dto = INSTANCE.detailToDto(setting);
//
//        // than
//        assertNotNull(dto);
//        assertEquals("type1", dto.getType());
//        assertEquals(detailDto1, dto.getDetails().get(0));
//        assertEquals(detailDto2, dto.getDetails().get(1));
//    }

//    @Test
//    public void shouldMapDtoToSetting() {
//        // given
//        setting.details = new ArrayList<>();
//        dto.getDetails().add(detail1);
//        dto.getDetails().addAll(Arrays.asList(detail1, detail2));
//        dto.setType("type1");
//        dto.setId(1L);
//
//        // when
//        setting = INSTANCE.dtoToSetting(dto);
//
//        // than
//        assertNotNull(setting);
//        assertEquals("type1", setting.getType());
//        assertEquals(detail1, setting.getDetails().get(0));
//        assertEquals(detail2, setting.getDetails().get(1));
//    }

    @Test
    public void shouldPartitionMapToSetting() {
        // given
        setting.setType("type1");
        dto.setType(null);

        // when
        INSTANCE.dtoToSetting(dto, setting);

        // than
        assertNotNull(setting.getType());
    }

    @Test
    public void shouldPartitionMapToDto() {
        // given
        setting.setType(null);
        dto.setType("type1");

        // when
        INSTANCE.settingToDto(setting, dto);

        // than
        assertNotNull(dto.getType());
    }
}
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
import static org.junit.Assert.assertEquals;

public class SettingMapperTest {

    private Setting setting = new Setting();
    private SettingDto settingDto = new SettingDto();
    private DetailDto detailDto1 = new DetailDto();
    private DetailDto detailDto2 = new DetailDto();

    @Before
    public void setUp() {
        OffsetDateTime time = OffsetDateTime.now();
        Detail detail1 = new Detail(1L, time, setting);
        Detail detail2 = new Detail(2L, time, setting);
        setting.details = new ArrayList<>();
        setting.details.addAll(Arrays.asList(detail1, detail2));
        settingDto.setDetails(new ArrayList<>());
        detailDto1.setDateTime(time);
        detailDto2.setDateTime(time);
    }

    @Test
    public void shouldMapSettingToDto() {
        // given
        setting.setId(1L);
        setting.setType("type1");
        setting.setItem1(1);

        // when
        settingDto = INSTANCE.settingToDto(setting);

        // than
        assertNotNull(settingDto);
        assertEquals("type1", settingDto.getType());
        assertEquals(detailDto1.getDateTime(), settingDto.getDetails().get(0).getDateTime());
        assertEquals(detailDto2.getDateTime(), settingDto.getDetails().get(1).getDateTime());
    }

    @Test
    public void shouldMapDtoToSetting() {
        // given
        setting.details = new ArrayList<>();
        settingDto.getDetails().add(detailDto1);
        settingDto.getDetails().addAll(Arrays.asList(detailDto1, detailDto2));
        settingDto.setType("type1");
        settingDto.setId(1L);

        // when
        setting = INSTANCE.dtoToSetting(settingDto);

        // than
        assertNotNull(setting);
        assertEquals("type1", setting.getType());
        assertEquals(settingDto.getId(), setting.getId());
        assertEquals(detailDto1.getDateTime(), setting.getDetails().get(0).getDateTime());
        assertEquals(detailDto2.getDateTime(), setting.getDetails().get(1).getDateTime());
    }

    @Test
    public void shouldPartitionMapToSetting() {
        // given
        setting.setType("type1");
        settingDto.setType(null);

        // when
        INSTANCE.dtoToSetting(settingDto, setting);

        // than
        assertNotNull(setting.getType());
    }

    @Test
    public void shouldPartitionMapToDto() {
        // given
        setting.setType(null);
        settingDto.setType("type1");

        // when
        INSTANCE.settingToDto(setting, settingDto);

        // than
        assertNotNull(settingDto.getType());
    }
}
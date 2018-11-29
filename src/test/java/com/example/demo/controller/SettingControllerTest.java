package com.example.demo.controller;

import com.example.demo.entity.Detail;
import com.example.demo.entity.Setting;
import com.example.demo.entity.SettingMapper;
import com.example.demo.service.DetailService;
import com.example.demo.service.SettingService;
import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest({SettingController.class, SettingMapper.class})
public class SettingControllerTest {

    private static final String URL = "/api/setting/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettingService settingService;

    @MockBean
    private DetailService detailService;

    private List<Setting> settings;
    private Setting setting;

    @Before
    public void setUp() {
        Setting setting1 = new Setting(1L, "setting1", 1, new ArrayList<>());
        Setting setting2 = new Setting(2L, "setting2", 1, new ArrayList<>());
        settings = Arrays.asList(setting1, setting2);
        setting = new Setting(3L, "setting3", 1, new ArrayList<>());
    }

    @Test
    public void getAll_ShouldReturnStatusOkAndExpectedContent() throws Exception {

        given(settingService.getAllSettings()).willReturn(settings);

        String expect = "[" +
                "{id: 1, type: setting1, item1: 1, details: []}" +
                ", {id: 2, type: setting2, item1: 1, details: []}" +
                "]";

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(expect));
    }

    @Test
    public void getSetting_ShouldReturnOne() throws Exception {

        given(settingService.getSettingById(1L)).willReturn(Optional.of(setting));
        String expect = "{id: 3, type: setting3, item1: 1, details: []}";

        mockMvc.perform(get(URL + "{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*", hasSize(4)))
                .andExpect(content().json(expect));
    }

    @Test
    public void getSetting_ShouldReturnNotFoundStatus() throws Exception {

        int id = 10;

        mockMvc.perform(get(URL + "{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(
                        String.format("Not found setting with id: %d", id)));
    }

    @Test
    public void addSetting_ShouldReturnCreatedStatus() throws Exception {

        String data = "{id: 1, type: setting1, item1: 1, details: []}";
        Gson g = new Gson();
        Setting setting = g.fromJson(data, Setting.class);

        mockMvc.perform(post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(g.toJson(setting)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteSetting_ShouldReturnGoneStatus() throws Exception {

        long id = 1L;
        given(settingService.getSettingById(id)).willReturn(Optional.of(setting));
        mockMvc.perform(delete(URL + "{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isGone());
    }

    @Test
    public void updateSetting_ShouldReturnOkStatusAndPatchNonCollectionFields() throws Exception {

        Long id = 1L;
        setting = new Setting(id, "setting3", 1, new ArrayList<>());
        given(settingService.getSettingById(id)).willReturn(Optional.of(setting));
        String expect = "{id:" + id + ", type: setting11, item1: 1}";
        Gson g = new Gson();
        Setting newContent = g.fromJson(expect, Setting.class);

        mockMvc.perform(patch(URL + "{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(g.toJson(newContent)))
                .andExpect(status().isOk())
                .andExpect(content().json(expect));
    }

    @Test
    public void updateSetting_ShouldUpdateDetailsCollection() throws Exception {

        Long id = 3L;
        Setting oldSetting = new Setting(id, "setting1", 1, new ArrayList<>());
        given(settingService.getSettingById(id)).willReturn(Optional.of(oldSetting));

        Detail detail = new Detail();
        OffsetDateTime time = OffsetDateTime.now(ZoneId.of("Z"));
        detail.setDateTime(time);
        Setting newSetting = new Setting(id, "setting3", 3, singletonList(detail));

        Gson g = Converters.registerOffsetDateTime(new GsonBuilder()).create();

        mockMvc.perform(patch(URL + "{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(g.toJson(newSetting)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath(".type").value("setting3"))
                .andExpect(jsonPath(".details[0].dateTime").value(time.toString()));
    }
}
package com.example.demo.controller;

import com.example.demo.model.Setting;
import com.example.demo.service.SettingService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SettingController.class)
public class SettingControllerTest {

    private static final String URL = "/api/setting/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettingService settingService;

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
    public void getAll() throws Exception {

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
}
package com.example.demo.controller;

import com.example.demo.model.Setting;
import com.example.demo.service.SettingService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Before
    public void setUp() {
        Setting setting1 = new Setting(1L, "setting1", 1, new ArrayList<>());
        Setting setting2 = new Setting(2L, "setting2", 1, new ArrayList<>());
        settings = Arrays.asList(setting1, setting2);
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

        Optional<Setting> setting = Optional.of(
                new Setting(1L, "setting1", 1, new ArrayList<>()));

        given(settingService.getSetting(1L)).willReturn(setting);
        String expect = "{id: 1, type: setting1, item1: 1, details: []}";

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
}
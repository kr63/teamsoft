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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SettingController.class)
public class SettingControllerTest {

    private static final String URL = "/api";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettingService settingService;

    @Before
    public void setUp() {
    }

    @Test
    public void getAll() throws Exception {

        Setting setting1 = new Setting(1L, "setting1", 1, new ArrayList<>());
        Setting setting2 = new Setting(2L, "setting2", 1, new ArrayList<>());
        List<Setting> settings = Arrays.asList(setting1, setting2);

        given(settingService.getAllSettings()).willReturn(settings);
        String expect = "[" +
                "{id: 1, type: setting1, item1: 1, details: []}" +
                ", {id: 2, type: setting2, item1: 1, details: []}" +
                "]";

        mockMvc.perform(get(URL + "/setting")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(expect));
    }
}
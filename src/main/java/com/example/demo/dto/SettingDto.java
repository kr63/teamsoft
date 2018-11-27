package com.example.demo.dto;

import com.example.demo.entity.Detail;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SettingDto {
    private Long id;
    private String type;
    private Integer item1;
    List<Detail> details;
}

package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDto {

    private Long id;
    private OffsetDateTime dateTime;

    @JsonBackReference
    @ManyToOne
    SettingDto settingDto;
}

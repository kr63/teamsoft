package com.example.demo.dto;

import com.example.demo.entity.Detail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingDto {
    private Long id;

    @Length(max = 20, message = "type value length must be 10 symbols max")
    private String type;

    @Max(value = 100, message = "item value must be 100 max")
    private Integer item1;

    @OneToMany(mappedBy = "setting", cascade = CascadeType.ALL)
    List<Detail> details;
}

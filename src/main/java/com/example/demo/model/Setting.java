package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Setting {

    @Id
    @GeneratedValue(generator = "setting_generator")
    @SequenceGenerator(
            name = "setting_generator",
            sequenceName = "setting_generator",
            initialValue = 6)
    private Long id;

    @NotNull(message = "type value must be not null")
    private String type;

    @Max(value = 100, message = "item value mast be 100 max")
    @Column(name = "item")
    private Integer item1;

    @NotNull(message = "details can't be null")
    @JsonManagedReference
    @OneToMany(mappedBy = "setting", cascade = CascadeType.ALL)
    List<Detail> details;
}

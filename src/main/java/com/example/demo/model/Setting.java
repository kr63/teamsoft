package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private String type;

    @Column(name = "item")
    private Integer item1;

    @JsonManagedReference
    @OneToMany(mappedBy = "setting", cascade = CascadeType.ALL)
    List<Detail> details;
}

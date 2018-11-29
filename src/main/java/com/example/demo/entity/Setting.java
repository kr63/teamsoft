package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
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

    @Length(max = 20, message = "type value length must be 10 symbols max")
    private String type;

    @Max(value = 100, message = "item value must be 100 max")
    @Column(name = "item")
    private Integer item1;

    @OneToMany(mappedBy = "setting",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH,
    })
    List<Detail> details;
}

package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Entity for Setting object")
public class Setting {

    @Id
    @GeneratedValue(generator = "setting_generator")
    @SequenceGenerator(
            name = "setting_generator",
            sequenceName = "setting_generator",
            initialValue = 6)
    private Long id;

    @ApiModelProperty(notes = "Type value must be 20 characters max")
    @Length(max = 20, message = "type value length must be 20 symbols max")
    private String type;

    @ApiModelProperty(notes = "Item1 value must be 100 max")
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

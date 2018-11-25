package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "setting_detail")
public class Detail {

    @Id
    @GeneratedValue(generator = "detail_generator")
    @SequenceGenerator(
            name = "detail_generator",
            sequenceName = "detail_generator",
            initialValue = 8)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "create_at")
    private OffsetDateTime dateTime;

    @JsonBackReference
    @ManyToOne
    Setting setting;
}

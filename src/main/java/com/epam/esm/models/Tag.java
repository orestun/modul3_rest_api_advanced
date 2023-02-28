package com.epam.esm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author orest uzhytchak
 * Entity Tag class
 * */
@Entity
@Table
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotNull(message = "Name field for tag can`t be null")
    @NotBlank(message = "Name field for tag should be input using letters A-Z or/and numbers 0-9")
    @Size(max = 30, message = "Name field length can`t be more than 30 chars")
    @Column(length = 30)
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {

    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

package com.epam.esm.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

/**
 * @author orest uzhytchak
 * Entity User class
 * */
@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Name field for user can`t be null")
    @NotBlank(message = "Name field for user should be input using letters A-Z")
    @Size(max = 30, message = "Name field length can`t be more than 30 chars")
    @Column(length = 30)
    private String name;

    @NotNull(message = "Surname field for user can`t be null")
    @NotBlank(message = "Surname field for user should be input using letters A-Z")
    @Size(max = 30, message = "Surname field length can`t be more than 30 chars")
    @Column(length = 30)
    private String surname;

    @NotNull(message = "Email field for user can`t be null")
    @Email(message = "Incorrect email input")
    private String email;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate birthDay;

}

package com.epam.esm.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDay;
}

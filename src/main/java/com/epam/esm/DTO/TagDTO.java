package com.epam.esm.DTO;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO extends RepresentationModel<TagDTO> {
    private Long id;
    private String name;

    public TagDTO(String name) {
        this.name = name;
    }
}

package com.epam.esm.DTO;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> {
    private Long id;
    private String name;
    private String description;
    private Integer duration;
    private BigDecimal price;
    private LocalDate createDate;
    private LocalDate lastUpdateDate;
    private Set<TagDTO> tags;

    public GiftCertificateDTO(String name,
                              String description,
                              Integer duration,
                              BigDecimal price,
                              LocalDate createDate,
                              LocalDate lastUpdateDate) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }
}

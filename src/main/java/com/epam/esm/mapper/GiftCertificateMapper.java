package com.epam.esm.mapper;

import com.epam.esm.DTO.GiftCertificateDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GiftCertificateMapper {

    private static final TagMapper tagMapper = new TagMapper();

    public GiftCertificateDTO toDto(GiftCertificate giftCertificate){
        GiftCertificateDTO giftCertificateDTO =
                new GiftCertificateDTO(
                        giftCertificate.getName(),
                        giftCertificate.getDescription(),
                        giftCertificate.getDuration(),
                        giftCertificate.getPrice(),
                        giftCertificate.getCreateDate(),
                        giftCertificate.getUpdateDate());
        giftCertificateDTO.
                setTags(
                        toTagsDtoSet(giftCertificate.getTags()));
        giftCertificateDTO.setId(giftCertificate.getId());
        return giftCertificateDTO;
    }

    public GiftCertificate toGiftCertificate(GiftCertificateDTO giftCertificateDTO){
        GiftCertificate giftCertificate =
                new GiftCertificate(
                        giftCertificateDTO.getName(),
                        giftCertificateDTO.getDescription(),
                        giftCertificateDTO.getDuration(),
                        giftCertificateDTO.getPrice());
        if(giftCertificateDTO.getTags()!=null){
            giftCertificate.
                    setTags(
                            toTagsSet(giftCertificateDTO.getTags()));
        }else{
            giftCertificate.setTags(new HashSet<>());
        }

        return giftCertificate;
    }

    private static Set<TagDTO> toTagsDtoSet(Set<Tag> tagSet){
        return tagSet.
                stream().
                map(tagMapper::toDto).
                collect(Collectors.toSet());
    }

    private static Set<Tag> toTagsSet(Set<TagDTO> tagDTOSet){
        return tagDTOSet.
                stream().
                map(tagMapper::toTag).
                collect(Collectors.toSet());
    }

}

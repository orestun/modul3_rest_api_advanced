package com.epam.esm.mapper;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.models.Tag;

public class TagMapper {
    public TagDTO toDto(Tag tag){
        TagDTO tagDTO = new TagDTO(tag.getName());
        tagDTO.setId(tag.getId());
        return tagDTO;
    }

    public Tag toTag(TagDTO tagDTO){
        return new Tag(tagDTO.getName());
    }
}

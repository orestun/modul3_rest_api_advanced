package com.epam.esm.controllers;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.models.Tag;
import com.epam.esm.hateoas.TagHateoas;
import com.epam.esm.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author orest uzhytchak
 * A controller tag class
 * */
@RestController
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;

    private final TagMapper tagMapper = new TagMapper();
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Controller GET method that return all got tags,
     * by calling a method of service layer
     * @see TagService#getAllTags(Integer, Integer)
     * @param page page number to be viewed (default value = 1)
     * @param pageSize number of objects that are going to be view in one page
     *                 (default value = 10)
     *
     * @return list of tags got from service layer
     * */
    @GetMapping
    public List<TagDTO> getAllTags(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ){
        List<TagDTO> tags =
                tagService.
                        getAllTags(page, pageSize).
                        stream().
                        map(tagMapper::toDto).
                        toList();
        return TagHateoas.linksForGettingAllTags(tags);
    }

    /**
     * Controller GET method that return the most widely used tag
     * for user that have the highest cost of all orders,
     * by calling a method of service layer
     * @see TagService#getTheMostWidelyTagOfUserWithTheHighestTotalOrderCost()
     *
     * @return the most widely used tag for user with the highest cost of all orders
     * */
    @GetMapping("most-widely-used-tag")
    public TagDTO getTheMostWidelyTagOfUserWithTheHighestTotalOrderCost(){
        return tagMapper.
                toDto(tagService.
                        getTheMostWidelyTagOfUserWithTheHighestTotalOrderCost());
    }

    /**
     * Controller POST method that add a new tag,
     * by calling a method of service layer
     * @see TagService#addNewTag(Tag)
     * @param tag tag that are going to be added in DB
     *
     * @return tag that was added in DB
     * */
    @PostMapping
    public TagDTO addNewTag(@RequestBody TagDTO tag){
        Tag createdTag = tagService.addNewTag(tagMapper.toTag(tag));
        return TagHateoas.linksForAddingNewTag(tagMapper.toDto(createdTag));
    }

    /**
     * Controller DELETE method that delete tag by id,
     * by calling a method of service layer
     * @see TagService#deleteTag(Long)
     * @param id id of tag that are going to be deleted
     *
     * */
    @DeleteMapping({"{id}"})
    public ResponseEntity<?> deleteTag(@PathVariable("id") Long id){
        tagService.deleteTag(id);
        return ResponseEntity.of(Optional.of(Map.of("Status", HttpStatus.OK)));
    }
}

package com.epam.esm.tag;

import com.epam.esm.giftCertificate.GiftCertificate;
import com.epam.esm.order.OrderService;
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
    public List<Tag> getAllTags(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ){
        List<Tag> tags =
                tagService.getAllTags(page, pageSize);
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
    public Tag getTheMostWidelyTagOfUserWithTheHighestTotalOrderCost(){
        return tagService.getTheMostWidelyTagOfUserWithTheHighestTotalOrderCost();
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
    public Tag addNewTag(@RequestBody Tag tag){
        Tag createdTag = tagService.addNewTag(tag);
        return TagHateoas.linksForAddingNewTag(createdTag);
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

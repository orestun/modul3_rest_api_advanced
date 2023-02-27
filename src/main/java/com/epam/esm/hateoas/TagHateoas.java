package com.epam.esm.hateoas;

import com.epam.esm.controllers.TagController;
import com.epam.esm.models.Tag;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author orest uzhytchak
 * Hateoas class that add links to tag object
 * */
public class TagHateoas {

    private static final Link getAllTagsLink =
            Link.of("http://localhost:8080/tag").
                    withRel("Get all tags").
                    withType("GET");
    private static Link deleteTagByIdLink(Long id) {
        return linkTo(methodOn(TagController.class).
                deleteTag(id)).
                withRel("Delete tag by id").
                withType("DELETE");
    }

    private static final Link getMostWidelyUsedTag =
            Link.of("http://localhost:8080/tag/most-widely-used-tag").
                    withRel("Get most widely tag for user with the highest cost of all orders").
                    withType("GET");

    /**
     * Hateoas method that add links to Tag objects got as
     * result of method {@link TagController#getAllTags(Integer, Integer)}
     *
     * @param tags list of tags that was get from DB
     *
     * @return tags list with links
     * */
    public static List<Tag> linksForGettingAllTags(
            List<Tag> tags){
        for(Tag tag: tags){
            tag.add(deleteTagByIdLink(tag.getId()));
            tag.add(getMostWidelyUsedTag);
        }
        return tags;
    }

    /**
     * Hateoas method that add links to Tag objects got as
     * result of method {@link TagController#addNewTag(Tag)}
     *
     * @param tag tag that was added to DB
     *
     * @return tag with links
     * */
    public static Tag linksForAddingNewTag(
            Tag tag){
        tag.add(getAllTagsLink);
        tag.add(deleteTagByIdLink(tag.getId()));
        tag.add(getMostWidelyUsedTag);
        return tag;
    }
}

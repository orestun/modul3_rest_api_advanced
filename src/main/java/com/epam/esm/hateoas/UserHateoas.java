package com.epam.esm.hateoas;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.controllers.UserController;
import org.springframework.hateoas.Link;

/**
 * @author orest uzhytchak
 * Hateoas class that add links to user object
 * */
public class UserHateoas {
    private static final Link getAllUsersLink =
            Link.of("http://localhost:8080/user").
                    withRel("Get all users").
                    withType("GET");

    /**
     * Hateoas method that add links to Tag objects got as
     * result of method {@link UserController#addNewUser(UserDTO)}
     *
     * @param user user object that was added in DB
     *
     * @return user object with links
     * */
    static public UserDTO linksForAddingNewUser(UserDTO user){
        user.add(getAllUsersLink);
        return user;
    }
}

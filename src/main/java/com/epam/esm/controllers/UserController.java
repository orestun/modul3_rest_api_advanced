package com.epam.esm.controllers;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.models.User;
import com.epam.esm.hateoas.UserHateoas;
import com.epam.esm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author orest uzhytchak
 * A controller user class
 * */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper = new UserMapper();
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Controller GET method that return all got users,
     * by calling a method of service layer
     * @see UserService#getAllUsers(Integer, Integer)
     * @param page page number to be viewed (default value = 1)
     * @param pageSize number of objects that are going to be view in one page
     *                 (default value = 10)
     *
     * @return list of users got from service layer
     * */
    @GetMapping
    public List<UserDTO> getAllUsers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize
    ){
        return userService.
                getAllUsers(page, pageSize).
                stream().map(userMapper::toDto).
                toList();
    }

    /**
     * Controller POST method that add a new user,
     * by calling a method of service layer
     * @see UserService#addNewUser(User)
     * @param user user object that are going to be added in DB
     *
     * @return user object that was added in DB
     * */
    @PostMapping
    public UserDTO addNewUser(@RequestBody UserDTO user){
        User createdUser = userService.
                addNewUser(userMapper.toUser(user));
        return UserHateoas.
                linksForAddingNewUser(userMapper.
                        toDto(createdUser));
    }
}

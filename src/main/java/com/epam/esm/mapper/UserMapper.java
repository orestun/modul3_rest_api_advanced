package com.epam.esm.mapper;

import com.epam.esm.DTO.UserDTO;
import com.epam.esm.models.User;

public class UserMapper {

    public UserDTO toDto(User user){
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getBirthDay());
    }
    public User toUser(UserDTO userDTO){
        return new User(
                userDTO.getName(),
                userDTO.getSurname(),
                userDTO.getEmail(),
                userDTO.getBirthDay());
    }
}

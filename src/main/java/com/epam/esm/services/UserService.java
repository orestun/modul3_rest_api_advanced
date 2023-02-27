package com.epam.esm.services;

import com.epam.esm.exception.DataValidationHandler;
import com.epam.esm.exception.HibernateValidationException;
import com.epam.esm.exception.ObjectAlreadyExistsException;
import com.epam.esm.models.User;
import com.epam.esm.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author orest uzhytchak
 * A service layer for user
 * */
@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Service layer method that return all got tags,
     * by calling a method of repository
     * @param page page number to be viewed
     * @param pageSize number of objects that are going to be view in one page
     *
     * @return list of tags got from repository
     * */
    public List<User> getAllUsers(Integer page, Integer pageSize){

        page-=1;

        return userRepository.
                findAll(PageRequest.of(page, pageSize)).
                get().
                collect(Collectors.toList());
    }

    /**
     * Service layer method that return all got tags,
     * by calling a method of repository
     * @param user user object that are going to be added in DB
     *
     * @return user object that was added in DB
     * */
    public User addNewUser(@Valid User user){
        DataValidationHandler<User> dataValidationHandler
                = new DataValidationHandler<>();
        String errors = dataValidationHandler.errorsRepresentation(user);
        if(!errors.isEmpty()){
            throw new HibernateValidationException(
                    errors,
                    40001L);
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ObjectAlreadyExistsException(
                    String.format("There is user with - '%s' email",user.getEmail()),
                    40901L);
        }
        return userRepository.save(user);
    }
}

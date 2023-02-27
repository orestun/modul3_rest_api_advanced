package com.epam.esm.user.service;

import com.epam.esm.exception.HibernateValidationException;
import com.epam.esm.exception.ObjectAlreadyExistsException;
import com.epam.esm.models.User;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setup(){
        userService = new UserService(userRepository);
    }

    @Test
    public void getAllGiftCertificateTest(){
        Page<User> users = new PageImpl<>(
                List.of(new User(),
                        new User(),
                        new User()));
        when(userRepository.
                findAll(PageRequest.of(0, 10))).thenReturn(users);
        assertEquals(users.stream().toList(),
                userService.getAllUsers(1,10));
    }

    @ParameterizedTest
    @CsvSource({
            "1, u_name, u_surname, e@gmail.com",
            "2, u_name2, u_surname2, e1@gmail.com",
    })
    public void successAddingNewUserTest(Long id, String name, String surname, String email){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setBirthDay(LocalDate.now());
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user,
                userService.addNewUser(user));
    }

    @ParameterizedTest
    @CsvSource({
            "1, , u_surname, e@gmail.com",
            "2, u_name2, , e1@gmail.com",
            "3, u_name, u_surname, e_gmail.com"
    })
    public void badDataWhileAddingNewUserTest(Long id, String name, String surname, String email){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setBirthDay(LocalDate.now());
        when(userRepository.existsByEmail(email)).thenReturn(false);
        HibernateValidationException exception = assertThrows(HibernateValidationException.class,
                ()->userService.addNewUser(user));
        assertEquals(HibernateValidationException.class,
                exception.getClass());
    }

    @ParameterizedTest
    @CsvSource({
            "1, name, u_surname, e@gmail.com",
            "2, name2, surname, e1@gmail.com",
            "3, u_name, u_surname, e@gmail.com"
    })
    public void alreadyExistentUserWithSuchEmailWhileAddingNewUserTest(Long id, String name, String surname, String email){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setBirthDay(LocalDate.now());
        when(userRepository.existsByEmail(email)).thenReturn(true);
        ObjectAlreadyExistsException exception = assertThrows(ObjectAlreadyExistsException.class,
                ()->userService.addNewUser(user));
        assertEquals(exception.getMessage(),
                String.format("There is user with - '%s' email",user.getEmail()));
    }

}

package com.epam.esm.generator;

import com.epam.esm.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author orest uzhytchak
 * A generator class that generate a new random User object
 * */
public class UserGenerator {

    static final List<String> names = List.of(
            "Tom",
            "Mike",
            "Alex",
            "Den",
            "Bob",
            "Steve",
            "Kate",
            "Ann",
            "Jasper",
            "Victoria",
            "Luna",
            "Tom");

    static final List<String> surnames = List.of(
            "Smith",
            "Johnson",
            "Williams",
            "Brown",
            "Jones",
            "Garcia",
            "Davis",
            "Thomas",
            "Taylor",
            "Clark");


    static public User generateRandomUser(int number){
        User user = new User();
        Random random = new Random();
        user.setName(names.
                get(random.
                        nextInt(0, names.size())));
        user.setSurname(surnames.
                get(random.
                        nextInt(0, surnames.size())));
        user.setEmail(String.format("%s%s%d@gmail.com",
                user.getName(),
                user.getSurname(),
                number));
        user.setBirthDay(UserGenerator.generateRandomBirth());
        return user;
    }

    static private LocalDate generateRandomBirth(){
        long startDate = LocalDate.of(1990,1,1).toEpochDay();
        long endDate = LocalDate.of(2010,1,1).toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startDate, endDate);

        return LocalDate.ofEpochDay(randomDay);
    }

}

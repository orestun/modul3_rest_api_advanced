package com.epam.esm.user.repository;

import com.epam.esm.giftcertificateprojectadvanced.GiftCertificateProjectAdvancedApplication;
import com.epam.esm.user.User;
import com.epam.esm.user.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = GiftCertificateProjectAdvancedApplication.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

}

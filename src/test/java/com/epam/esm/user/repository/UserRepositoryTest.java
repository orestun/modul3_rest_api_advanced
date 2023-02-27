package com.epam.esm.user.repository;

import com.epam.esm.giftcertificateprojectadvanced.GiftCertificateProjectAdvancedApplication;
import com.epam.esm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = GiftCertificateProjectAdvancedApplication.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

}

package com.epam.esm.tag.repository;

import com.epam.esm.giftcertificateprojectadvanced.GiftCertificateProjectAdvancedApplication;
import com.epam.esm.models.Tag;
import com.epam.esm.repositories.TagRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = GiftCertificateProjectAdvancedApplication.class)
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    public void setup(){
        List<Tag> tagList =
                List.of(new Tag(1L, "tag1"),
                        new Tag(2L, "tag2"),
                        new Tag(3L, "tag3"));
        tagRepository.saveAll(tagList);
    }

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "3, true",
            "6, false"
    })
    public void isExistTag(Long id, boolean expected){
        setup();
        assertEquals(expected, tagRepository.existsById(id));
    }

    @ParameterizedTest
    @CsvSource({
            "tag1, true",
            "tag8, false",
            "tag3, true"
    })
    public void isExistTagByName(String name, boolean expected){
        setup();
        assertEquals(expected, tagRepository.existsByName(name));
    }
}

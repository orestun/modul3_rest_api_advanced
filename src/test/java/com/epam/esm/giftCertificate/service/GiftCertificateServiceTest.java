package com.epam.esm.giftCertificate.service;

import com.epam.esm.exception.HibernateValidationException;
import com.epam.esm.exception.ItemNotFoundException;
import com.epam.esm.exception.NotAllowedParameterException;
import com.epam.esm.giftCertificate.GiftCertificate;
import com.epam.esm.giftCertificate.GiftCertificateRepository;
import com.epam.esm.giftCertificate.GiftCertificateService;
import com.epam.esm.tag.Tag;
import com.epam.esm.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GiftCertificateServiceTest {

    @InjectMocks
    GiftCertificateService giftCertificateService;
    @Mock
    GiftCertificateRepository giftCertificateRepository;
    @Mock
    TagRepository tagRepository;

    @BeforeEach
    public void setup(){
        giftCertificateService = new GiftCertificateService(
                giftCertificateRepository,
                tagRepository);
    }

    @Test
    public void getFirstTenGiftCertificatesTest(){
        Page<GiftCertificate> giftCertificateList =
                new PageImpl<>(
                        List.of(new GiftCertificate(),
                                new GiftCertificate(),
                                new GiftCertificate()));
        when(giftCertificateRepository.findAll(PageRequest.of(0,10))).
                thenReturn(giftCertificateList);

        assertEquals(giftCertificateList.stream().toList(),
                giftCertificateService.getAllGiftCertificates(1,10));
    }

    @ParameterizedTest
    @CsvSource({
            "   , test, 10, 2,",
            ", test, 10, 3"
    }
    )
    public void nullNameFieldWhileAddingNewGiftCertificateTest(String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate =
                new GiftCertificate(
                        name,
                        description,
                        duration,
                        price);

        HibernateValidationException exception =
                assertThrows(HibernateValidationException.class,
                        ()->giftCertificateService.addNewGiftCertificate(giftCertificate));
        assertTrue(exception.getMessage().contains("Name field for gift certificate can`t be null; "));
    }

    @ParameterizedTest
    @CsvSource({
            "'   ', test, 10, 1",
            "'', test, 10, 2,",
            "' ', test, 10, 3"
    }
    )
    public void blankNameFieldWhileAddingNewGiftCertificateTest(String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate =
                new GiftCertificate(
                        name,
                        description,
                        duration,
                        price);

        HibernateValidationException exception =
                assertThrows(HibernateValidationException.class,
                        ()->giftCertificateService.addNewGiftCertificate(giftCertificate));
        assertTrue(exception.getMessage().contains("Name field for gift certificate should be input using letters A-Z or/and numbers 0-9; "));
    }

    @ParameterizedTest
    @CsvSource({
            "very_long_name_That_is_more_than_30_chars, test, 10, 1",
            "_name_test_name_test_name_test_, test, 10, 2,"
    }
    )
    public void tooLongNameFieldWhileAddingNewGiftCertificateTest(String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate =
                new GiftCertificate(
                        name,
                        description,
                        duration,
                        price);

        HibernateValidationException exception =
                assertThrows(HibernateValidationException.class,
                        ()->giftCertificateService.addNewGiftCertificate(giftCertificate));
        assertTrue(exception.getMessage().contains("Name field length for gift certificate can`t be more than 30 chars; "));
    }

    @ParameterizedTest
    @CsvSource({
            "test_name_1, test, 10, 1",
            "test_name_2, test, 10, 2"
    })
    public void accessAddingGiftCertificateWithExistedTag(String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate =
                new GiftCertificate(
                        name,
                        description,
                        duration,
                        price);
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(
                new Tag("tag1"),
                new Tag("tag2")));
        giftCertificate.setTags(tagSet);
        when(tagRepository.existsByName("tag1")).thenReturn(true);
        when(tagRepository.existsByName("tag2")).thenReturn(true);
        when(tagRepository.findByName("tag1")).thenReturn(new Tag());
        when(tagRepository.findByName("tag2")).thenReturn(new Tag());
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);

        assertEquals(giftCertificate,
                giftCertificateService.addNewGiftCertificate(giftCertificate));
    }

    @ParameterizedTest
    @CsvSource({
            "test_name_1, test, 10, 1",
            "test_name_2, test, 10, 2"
    })
    public void accessAddingGiftCertificateWithNotExistedTag(String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate =
                new GiftCertificate(
                        name,
                        description,
                        duration,
                        price);
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(
                new Tag("tag1"),
                new Tag("tag2")));
        giftCertificate.setTags(tagSet);
        when(tagRepository.existsByName("tag1")).thenReturn(false);
        when(tagRepository.existsByName("tag2")).thenReturn(false);
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);

        assertEquals(giftCertificate,
                giftCertificateService.addNewGiftCertificate(giftCertificate));
    }

    @ParameterizedTest
    @CsvSource({
            "asc, name",
            "desc, name",
            "ASC, name"
    })
    public void successGettingSortedGiftCertificatesTest(
            String direction,
            String sortingAttribute){
        Page<GiftCertificate> giftCertificates = new PageImpl<>(List.of(
                new GiftCertificate(),
                new GiftCertificate(),
                new GiftCertificate()
        ));
        giftCertificates.stream().toList().get(0).setName("a");
        giftCertificates.stream().toList().get(1).setName("c");
        giftCertificates.stream().toList().get(2).setName("b");

        when(giftCertificateRepository.findAll(PageRequest.of(0, 10,
                Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), sortingAttribute)))).thenReturn(giftCertificates);
        assertEquals(giftCertificates.stream().toList(),
                giftCertificateService.getSortedGiftCertificates(
                        direction,
                        sortingAttribute,
                        1,10));

    }

    @ParameterizedTest
    @CsvSource({
            "a sc, name",
            "'', name",
            "descasc, name",
            "descending, name"
    })
    public void badGettingSortedGiftCertificatesTest(
            String direction,
            String sortingAttribute){
        Page<GiftCertificate> giftCertificates = new PageImpl<>(List.of(
                new GiftCertificate(),
                new GiftCertificate(),
                new GiftCertificate()
        ));
        giftCertificates.stream().toList().get(0).setName("a");
        giftCertificates.stream().toList().get(1).setName("c");
        giftCertificates.stream().toList().get(2).setName("b");

        when(giftCertificateRepository.findAll(PageRequest.of(0, 10))).thenReturn(giftCertificates);
        NotAllowedParameterException exception =
                assertThrows(NotAllowedParameterException.class,
                        ()->giftCertificateService.getSortedGiftCertificates(direction,
                                sortingAttribute, 1, 10));
        assertEquals(exception.getMessage(), "Only ASC or DESC attributes are allowed");
    }

    @ParameterizedTest
    @CsvSource({
            "ascc, desc",
            "ascending, descending",
            "as c, AS C"
    })
    public void badGettingDoubleSortedGiftCertificatesTest(
            String nameDirection,
            String createDateDirection){
        Page<GiftCertificate> giftCertificates = new PageImpl<>(List.of(
                new GiftCertificate(),
                new GiftCertificate(),
                new GiftCertificate()
        ));


        PageRequest pageRequest = PageRequest.of(0, 10);

        when(giftCertificateRepository.
                findAll(pageRequest)).
                thenReturn(giftCertificates);
        NotAllowedParameterException exception =
                assertThrows(NotAllowedParameterException.class,
                        ()->giftCertificateService.getSortedGiftCertificatesByNameAndCreateDate(nameDirection,
                                createDateDirection,
                                1, 10));
        assertEquals(exception.getMessage(), "Only ASC or DESC attributes are allowed");
    }

    @ParameterizedTest
    @CsvSource({
            "firth",
            "second",
            "third"
    })
    public void getGiftCertificatesByName(String name){
        Page<GiftCertificate> giftCertificates = new PageImpl<>(
                List.of(new GiftCertificate()));
        when(giftCertificateRepository.findByPartOfName("%"+name+"%",PageRequest.of(0,10))).
                thenReturn(giftCertificates);
        assertEquals(giftCertificates.stream().toList(),
                giftCertificateService.getGiftCertificatesByName(name,1,10));
    }

    @ParameterizedTest
    @CsvSource({
            "firth",
            "second",
            "third"
    })
    public void getGiftCertificatesByDescription(String description){
        Page<GiftCertificate> giftCertificates = new PageImpl<>(
                List.of(new GiftCertificate()));
        when(giftCertificateRepository.findByPartOfDescription("%"+description+"%",PageRequest.of(0,10))).
                thenReturn(giftCertificates);
        assertEquals(giftCertificates.stream().toList(),
                giftCertificateService.getGiftCertificatesByDescription(description,1,10));
    }

    @ParameterizedTest
    @CsvSource({
            "tag1, tag2, tag3",
            "tag4, tag5, ''"
    })
    public void getGiftCertificateBySeveralTags(String param1, String param2, String param3){
        Set<String> tags = new HashSet<>(List.of(param1,param2,param3));
        List<GiftCertificate> giftCertificates = List.of(
                new GiftCertificate(),
                new GiftCertificate(),
                new GiftCertificate()
        );
        when(giftCertificateRepository.getGiftCertificateBySeveralTags(tags, 0,10)).
                thenReturn(giftCertificates);
        assertEquals(giftCertificates, giftCertificateService.getGiftCertificateBySeveralTags(tags, 1,10));

    }

    @ParameterizedTest
    @CsvSource({
            "1,test_name_1, test, 10, 1",
            "2,test_name_2, test, 10, 2",
            "3,test_name_3, test, 10, 300"
    })
    public void updateGiftCertificateTest(Long id,String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate = new GiftCertificate(name, description,duration,price);
        giftCertificate.setId(id);
        when(giftCertificateRepository.existsById(id)).thenReturn(true);
        when(giftCertificateRepository.findById(id)).
                thenReturn(Optional.of(giftCertificate));
        when(giftCertificateRepository.save(giftCertificate)).
                thenReturn(giftCertificate);

        assertEquals(giftCertificate,
                giftCertificateService.
                        updateGiftCertificateBySomeFields(id, name, description, price, duration));
    }


    @ParameterizedTest
    @CsvSource({
            "1,test_name_1, test, 10, 1",
            "2,test_name_2, test, 10, 2",
            "3,test_name_3, test, 10, 300"
    })
    public void nonExistentGiftCertificateForUpdatingSomeFieldsTest(Long id,String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate = new GiftCertificate(name, description,duration,price);
        giftCertificate.setId(id);
        when(giftCertificateRepository.existsById(id)).thenReturn(false);
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
                ()->giftCertificateService.
                        updateGiftCertificateBySomeFields(id, name, description, price, duration));
        assertEquals(exception.getMessage(), String.format("There is not gift certificate with (id=%d)",id));
    }

    @ParameterizedTest
    @CsvSource({
            "1,test_name_1, test, 10, 1",
            "2,test_name_2, test, 10, 2",
            "3,test_name_3, test, 10, 300"
    })
    public void nonExistentGiftCertificateForUpdatingTest(Long id,String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate = new GiftCertificate(name, description,duration,price);
        giftCertificate.setId(id);
        when(giftCertificateRepository.existsById(id)).thenReturn(false);
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
                ()->giftCertificateService.
                        updateGiftCertificate(id, giftCertificate));
        assertEquals(exception.getMessage(), String.format("There is not gift certificate with (id=%d)",id));
    }

    @ParameterizedTest
    @CsvSource({
            "1,, test, 10, 1",
            "2,test_name_2, test, -10, 2",
            "3,test_name_3, '', 10, 300"
    })
    public void badDataForUpdatingGiftCertificateTest(Long id,String name, String description, Integer duration, BigDecimal price){
        GiftCertificate giftCertificate = new GiftCertificate(name, description,duration,price);
        giftCertificate.setId(id);
        when(giftCertificateRepository.existsById(id)).thenReturn(false);
        HibernateValidationException exception = assertThrows(HibernateValidationException.class,
                ()->giftCertificateService.
                        updateGiftCertificate(id, giftCertificate));
        assertEquals(exception.getClass(), HibernateValidationException.class);
    }


    @ParameterizedTest
    @CsvSource({
            "1",
            "65",
            "9089"
    })
    public void successDeletingGiftCertificateByIdTest(Long id){
        when(giftCertificateRepository.existsById(id)).thenReturn(true);
        assertEquals(id, giftCertificateService.deleteGiftCertificate(id));
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "65",
            "9089"
    })
    public void nonExistentGiftCertificateForDeletingByIdTest(Long id){
        when(giftCertificateRepository.existsById(id)).thenReturn(false);
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class,
                ()->giftCertificateService.deleteGiftCertificate(id));
        assertEquals(exception.getMessage(),
                String.format("There is not gift certificate with (id=%d)",id));
    }
}

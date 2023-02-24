package com.epam.esm.giftCertificate;

import org.springframework.hateoas.Link;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author orest uzhytchak
 * A hateoas class that add links to gift certificate object
 * */
public class GiftCertificateHateoas {

    static private Link updateGiftCertificateById(Long id, GiftCertificate giftCertificate){
        return linkTo(methodOn(GiftCertificateController.class).updateGiftCertificate(
                giftCertificate.getId(),
                giftCertificate)).
                withRel("Update gift certificate by id").
                withType("PATCH");
    }

    static private Link deleteGiftCertificateById(Long id){
        return linkTo(methodOn(GiftCertificateController.class).
                deleteGiftCertificate(id)).
                withRel("Delete gift certificate by id").
                withType("DELETE");
    }

    static final private Link getAllGiftCertificatesLink =
            linkTo(methodOn(GiftCertificateController.class).
                    getAllGiftCertificates(1,10)).
                    withRel("Get all gift certificates").
                    withType("GET");

    static final private Link sortGiftCertificateByCreateDateLink = Link.of("http://localhost:8080/certificate/sort?create-date=asc").
            withRel("Sort gift certificates by create date (asc)").
            withType("GET");
    static final private Link sortGiftCertificateByNameLink = Link.of("http://localhost:8080/certificate/sort?name=asc").
            withRel("Sort gift certificates by name (asc)").
            withType("GET");
    static final private Link sortGiftCertificateByPriceLink = Link.of("http://localhost:8080/certificate/sort?price=asc").
            withRel("Sort gift certificates by price (asc)").
            withType("GET");
    static final private Link doubleSortGiftCertificateLink = Link.of("http://localhost:8080/certificate/double-sort?name=asc&create-date=asc").
            withRel("Double sort gift certificates by name and create date (asc)").
            withType("GET");
    static final private Link findGiftCertificateByNameLink = Link.of("http://localhost:8080/certificate/find-by-name?name=your-input-name").
            withRel("Find gift certificate by name").
            withType("GET");
    static final private Link findGiftCertificateByDescriptionLink = Link.of("http://localhost:8080/certificate/find-by-description?description=your-input-description").
            withRel("Find gift certificate by description").
            withType("GET");
    static final private Link findGiftCertificateBySeveralTagsLink = Link.of("http://localhost:8080/certificate/find-by-several-tags/your-tag-1, your-tag2, your-tag-3").
            withRel("Find gift certificate by several tags").
            withType("GET");

    /**
     * Hateoas method that add links to GiftCertificate objects got as
     * result of method {@link GiftCertificateController#getAllGiftCertificates(Integer, Integer)}
     *
     * @param giftCertificateList list of gift certificates that was get from DB
     *
     * @return gift certificates list with links
     * */
    static public List<GiftCertificate> linksForGettingGiftCertificates(
            List<GiftCertificate> giftCertificateList){
        for(GiftCertificate certificate: giftCertificateList){
            Long id = certificate.getId();
            certificate.add(updateGiftCertificateById(id,certificate));
            certificate.add(deleteGiftCertificateById(id));
            certificate.add(sortGiftCertificateByCreateDateLink);
            certificate.add(sortGiftCertificateByNameLink);
            certificate.add(sortGiftCertificateByPriceLink);
            certificate.add(findGiftCertificateByNameLink);
            certificate.add(findGiftCertificateByDescriptionLink);
            certificate.add(findGiftCertificateBySeveralTagsLink);
            certificate.add(findGiftCertificateByNameLink);
        }
        return giftCertificateList;
    }

    /**
     * Hateoas method that add links to GiftCertificate objects got as
     * result of method {@link GiftCertificateController#getSortedGiftCertificates(String, String, String, Integer, Integer)},
     * and {@link GiftCertificateController#getSortedGiftCertificatesByNameAndCreateDate(String, String, Integer, Integer)}
     *
     * @param giftCertificateList list of gift certificates that was get from DB
     *
     * @return gift certificates list with links
     * */
     static public List<GiftCertificate> linksForSortingGiftCertificates(
             List<GiftCertificate> giftCertificateList){
         for(GiftCertificate certificate: giftCertificateList) {
             Long id = certificate.getId();
             certificate.add(updateGiftCertificateById(id,certificate));
             certificate.add(deleteGiftCertificateById(id));
             certificate.add(findGiftCertificateByNameLink);
             certificate.add(findGiftCertificateByDescriptionLink);
             certificate.add(findGiftCertificateBySeveralTagsLink);
             certificate.add(findGiftCertificateByNameLink);
         }
         return giftCertificateList;
     }

    /**
     * Hateoas method that add links to GiftCertificate objects got as
     * result of method {@link GiftCertificateController#getGiftCertificatesByName(String, Integer, Integer)}
     * and {@link GiftCertificateController#getGiftCertificatesByDescription(String, Integer, Integer)}
     *
     * @param giftCertificateList list of gift certificates that was get from DB
     *
     * @return gift certificates list with links
     * */
     static public List<GiftCertificate> linksForGettingGiftCertificatesByNameAndByDescription(
             List<GiftCertificate> giftCertificateList){
        for (GiftCertificate certificate:giftCertificateList){
            Long id = certificate.getId();
            certificate.add(updateGiftCertificateById(id,certificate));
            certificate.add(deleteGiftCertificateById(id));
            certificate.add(sortGiftCertificateByCreateDateLink);
            certificate.add(sortGiftCertificateByNameLink);
            certificate.add(sortGiftCertificateByPriceLink);
            certificate.add(findGiftCertificateBySeveralTagsLink);
            certificate.add(findGiftCertificateByNameLink);
        }
        return giftCertificateList;
     }

    /**
     * Hateoas method that add links to GiftCertificate objects got as
     * result of method {@link GiftCertificateController#getGiftCertificateBySeveralTags(String[], Integer, Integer)}
     *
     * @param giftCertificateList list of gift certificates that was get from DB
     *
     * @return gift certificates list with links
     * */
     static public List<GiftCertificate> linksForGettingCertificatesBySeveralTags(
             List<GiftCertificate> giftCertificateList
     ){
         for(GiftCertificate certificate:giftCertificateList){
             Long id = certificate.getId();
             certificate.add(updateGiftCertificateById(id,certificate));
             certificate.add(deleteGiftCertificateById(id));
             certificate.add(sortGiftCertificateByCreateDateLink);
             certificate.add(sortGiftCertificateByNameLink);
             certificate.add(sortGiftCertificateByPriceLink);
             certificate.add(findGiftCertificateByNameLink);
             certificate.add(findGiftCertificateByDescriptionLink);
             certificate.add(findGiftCertificateByNameLink);
         }
         return giftCertificateList;
     }

    /**
     * Hateoas method that add links to GiftCertificate objects got as
     * result of method {@link GiftCertificateController#updateGiftCertificate(Long, GiftCertificate)}
     * and {@link GiftCertificateController#updateGiftCertificateBySomeFields(Long, String, String, BigDecimal, Integer)}
     *
     * @param certificate gift certificate that was updated
     *
     * @return gift certificate with links
     * */
     static public GiftCertificate linksForUpdateGiftCErtificate(
             GiftCertificate certificate){
         Long id = certificate.getId();
         certificate.add(deleteGiftCertificateById(id));
         certificate.add(getAllGiftCertificatesLink);
         return certificate;
     }

    /**
     * Hateoas method that add links to GiftCertificate object got as
     * result of method {@link GiftCertificateController#addNewGiftCertificate(GiftCertificate)}
     *
     * @param certificate gift certificate that was added in DB
     *
     * @return gift certificate with links
     * */
    static public GiftCertificate linksForAddingNewGiftCertificate(
            GiftCertificate certificate){
        Long id = certificate.getId();
        certificate.add(updateGiftCertificateById(id, certificate));
        certificate.add(deleteGiftCertificateById(id));
        certificate.add(getAllGiftCertificatesLink);
        return certificate;
    }
}

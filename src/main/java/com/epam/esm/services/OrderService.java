package com.epam.esm.services;

import com.epam.esm.exception.ItemNotFoundException;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.repositories.GiftCertificateRepository;
import com.epam.esm.models.Order;
import com.epam.esm.repositories.OrderRepository;
import com.epam.esm.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author orest uzhytchak
 * A service layer for Order
 * */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    /**
     * Service layer method that return all got orders,
     * by calling a method of repository
     * @param page page number to be viewed
     * @param pageSize number of objects that are going to be view in one page
     *
     * @return list of orders got from repository
     * */
    public List<Order> getAllOrders(Integer page,Integer pageSize){

        page-=1;

        return orderRepository.
                findAll(PageRequest.of(page, pageSize)).
                get().
                collect(Collectors.toList());
    }

    /**
     * Service layer method that add a new order,
     * by calling a method of repository
     * @param userID id of user that making an order
     * @param giftCertificateID id of gift certificate that adding to the order
     *
     * @return the object of order that was added
     * */
    public Order addNewOrder(Long userID, Long giftCertificateID){
        if(!userRepository.existsById(userID)){
            throw new ItemNotFoundException(
                    String.format("There is not user with such (id=%d)",userID),
                    40401L);
        }
        if(!giftCertificateRepository.existsById(giftCertificateID)){
            throw new ItemNotFoundException(
                    String.format("There is not gift certificate with such (id=%d)",giftCertificateID),
                    40401L);
        }

        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(giftCertificateID);
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.of("+02:00"));
        Order order = new Order();
        order.setUserID(userID);
        order.setGiftCertificateID(giftCertificateID);
        giftCertificate.ifPresent(certificate -> order.setCost(certificate.getPrice()));
        order.setTimeOfPurchase(currentDateTime);

        orderRepository.save(order);
        return order;
    }

    public List<Order> getOrdersByUserId(Long userID,
                                         Integer page,
                                         Integer pageSize){
        page-=1;
        if(!userRepository.existsById(userID)){
            throw new ItemNotFoundException(
                    String.format("There is not such user with (id = %d)",userID),
                    40401L);
        }

        return orderRepository.
                findByUserID(
                        userID,
                        PageRequest.of(page, pageSize)).
                stream().toList();
    }
}

package com.epam.esm.order;

import com.epam.esm.giftCertificate.GiftCertificateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author orest uzhytchak
 * A controller for Order
 * */
@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    /**
     * Controller GET method that return all got orders,
     * by calling a method of service layer
     * @see OrderService#getAllOrders(Integer, Integer)
     * @param page page number to be viewed (default value = 1)
     * @param pageSize number of objects that are going to be view in one page
     *                 (default value = 10)
     *
     * @return list of orders got from service layer
     * */
    @GetMapping
    public List<Order> getAllOrders(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize){
        return orderService.getAllOrders(page, pageSize);
    }

    /**
     * Controller POST method that add a new order,
     * by calling a method of service layer
     * @see OrderService#addNewOrder(Long, Long)
     * @param userID id of user that making an order
     * @param giftCertificateID id of gift certificate that adding to the order
     *
     * @return the object of order that was added
     * */
    @PostMapping
    public Order addNewOrder(@RequestParam("user-ID") Long userID,@RequestParam("gift-certificate-ID") Long giftCertificateID){
        return orderService.addNewOrder(userID, giftCertificateID);
    }
}

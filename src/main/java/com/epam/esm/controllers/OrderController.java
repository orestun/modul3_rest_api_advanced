package com.epam.esm.controllers;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.models.Order;
import com.epam.esm.services.OrderService;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
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
    private final OrderMapper orderMapper = new OrderMapper();

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
    public List<OrderDTO> getAllOrders(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize){
        return orderService.
                getAllOrders(page, pageSize).
                stream().
                map(orderMapper::toOrderDto).
                toList();
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
    public OrderDTO addNewOrder(@RequestParam("user-ID") Long userID, @RequestParam("gift-certificate-ID") Long giftCertificateID){
        return orderMapper.
                toOrderDto(orderService.addNewOrder(userID, giftCertificateID));
    }

    @GetMapping("{user-ID}")
    public List<OrderDTO> getOrdersByUserId(
            @PathVariable("user-ID") Long userId,
            @Nullable @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Nullable @RequestParam(value = "page-size", defaultValue = "10") Integer pageSize){
        return orderService.getOrdersByUserId(
                userId, page, pageSize).
                stream().
                map(orderMapper::toOrderDto).
                toList();
    }
}

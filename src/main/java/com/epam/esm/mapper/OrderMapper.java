package com.epam.esm.mapper;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.models.Order;

public class OrderMapper {
    public OrderDTO toOrderDto(Order order){
        return new OrderDTO(
                order.getId(),
                order.getUserID(),
                order.getGiftCertificateID(),
                order.getCost(),
                order.getTimeOfPurchase());
    }
}

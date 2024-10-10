package com.nus.edu.se.delivery.service.orderstatus;

import com.nus.edu.se.delivery.model.GroupFoodOrder;
import com.nus.edu.se.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OnDeliveryCommand implements OrderStatusCommandInterface {

    @Autowired
    private final DeliveryService deliveryService;

    public OnDeliveryCommand(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public void execute(String orderId) {
        if (orderId == null) {
            throw new NullPointerException("Order ID cannot be null");
        }
        deliveryService.updateStatus(orderId, GroupFoodOrder.Status.ON_DELIVERY);
    }
}

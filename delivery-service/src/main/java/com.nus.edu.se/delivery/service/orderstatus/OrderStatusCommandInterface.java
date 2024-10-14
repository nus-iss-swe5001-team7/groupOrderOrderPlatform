package com.nus.edu.se.delivery.service.orderstatus;

public interface OrderStatusCommandInterface {
    void execute(String orderId);
}

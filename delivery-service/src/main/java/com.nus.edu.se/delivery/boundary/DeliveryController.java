package com.nus.edu.se.delivery.boundary;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.nus.edu.se.delivery.dto.GroupFoodOrderList;
import com.nus.edu.se.delivery.service.DeliveryService;
import com.nus.edu.se.delivery.service.orderstatus.OrderStatusService;
import com.nus.edu.se.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("deliveryAPI")
public class DeliveryController {

    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private DeliveryService deliveryService;

    @PutMapping("/onDelivered/{orderId}")
    public ResponseEntity updateStatusToOnDelivery(@PathVariable("orderId") String orderId) {
        try {
            orderStatusService.onDelivery(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("fail_message", "updateStatusToOnDelivery Not found GroupOrder with orderId");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/delivered/{orderId}")
    public ResponseEntity updateStatusToDelivered(@PathVariable("orderId") String orderId) {
        try {
            orderStatusService.delivered(orderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("fail_message", "updateStatusToReadyForDelivery Not found GroupOrder with orderId");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/getAllGroupOrdersForDelivery")
    public ResponseEntity<List<GroupFoodOrderList>> getOrdersForDeliveryStaff(@RequestParam UUID userId, @RequestParam String location, HttpServletRequest request)  {
        String token = deliveryService.resolveToken(request);
        List<GroupFoodOrderList> filteredOrders =   deliveryService.getGroupFoodOrderListByUserId(userId, location, token);
        return ResponseEntity.ok(filteredOrders);
    }

}
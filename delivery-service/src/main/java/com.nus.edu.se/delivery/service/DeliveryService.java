package com.nus.edu.se.delivery.service;

import com.nus.edu.se.delivery.dao.DeliveryRepository;
import com.nus.edu.se.delivery.dto.GroupFoodOrderList;
import com.nus.edu.se.delivery.model.GroupFoodOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    private final WebClient.Builder webClientBuilder;

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public List<GroupFoodOrderList> getAllGroupOrders(String token) {
        return getAllGroupOrders(null);
    }

    @Transactional
    public GroupFoodOrder updateStatus(String orderId, GroupFoodOrder.Status status) {
        UUID orderUUID = UUID.fromString(orderId);
        GroupFoodOrder groupFoodOrder = new GroupFoodOrder();
        Optional<GroupFoodOrder> groupOrder = deliveryRepository.findById(orderUUID);

        if (groupOrder.isPresent()) {
            groupFoodOrder = groupOrder.get();
            groupFoodOrder.setId(orderUUID);
            groupFoodOrder.setStatus(status);
            if (status== GroupFoodOrder.Status.DELIVERED || status== GroupFoodOrder.Status.ON_DELIVERY){
                groupFoodOrder.setGroupOrderDeliveryTime(new Date());
            }
            groupFoodOrder = deliveryRepository.saveAndFlush(groupFoodOrder);
        } else {
            throw new RuntimeException("Not found GroupOrder with orderId = " + orderId);
        }
        return groupFoodOrder;
    }


    public List<GroupFoodOrderList> getGroupFoodOrderListByUserId(UUID userId,  String location, String token){
        String uri = String.format("http://order-service/groupFoodOrdersAPI/getOrdersForDeliveryStaff?userId=%s&location=%s", userId, location);

        Mono<List<GroupFoodOrderList>> response = webClientBuilder.build()
                .get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GroupFoodOrderList>>() {});

        return response.block();
    }


}

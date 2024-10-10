package com.nus.edu.se.delivery.dao;

import com.nus.edu.se.delivery.model.GroupFoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<GroupFoodOrder, UUID> {

    Optional<GroupFoodOrder> findGroupFoodOrderById(UUID id);
}

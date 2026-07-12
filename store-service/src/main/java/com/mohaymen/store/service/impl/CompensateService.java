package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.Order;
import com.mohaymen.store.model.OrderItem;
import com.mohaymen.store.model.OrderStatus;
import com.mohaymen.store.model.ProductInventory;
import com.mohaymen.store.repository.OrderRepository;
import com.mohaymen.store.repository.ProductInventoryRepository;
import com.mohaymen.store.service.ICompensateService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: Mehran
 * Created on: 7/12/26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CompensateService implements ICompensateService {

    private final OrderRepository orderRepository;
    private final ProductInventoryRepository inventoryRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void releaseExpiredReservations() {

        log.info("Starting compensation service...");
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(15);

        List<Order> expiredOrders =
                orderRepository.findByStatusAndCreatedAtBefore(OrderStatus.AWAITING_PAYMENT, threshold);

        for (Order order : expiredOrders) {
            order.setStatus(OrderStatus.CANCELED);

            for (OrderItem item : order.getItems()) {
                ProductInventory inventory =
                        inventoryRepository.findById(item.getProduct().getId()).orElseThrow();
                inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
            }
        }
    }
}

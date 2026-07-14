package com.mohaymen.store.service.impl;

import com.mohaymen.store.model.*;
import com.mohaymen.store.repository.OrderRepository;
import com.mohaymen.store.repository.OutboxRepository;
import com.mohaymen.store.service.ICompensateService;
import com.mohaymen.store.service.IInventoryService;
import com.mohaymen.store.service.IOrderPaymentService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final IInventoryService inventoryService;
    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final IOrderPaymentService orderPaymentService;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void releaseExpiredReservations() {

        log.info("Starting compensation service...");
        //        LocalDateTime threshold = LocalDateTime.now().minusMinutes(15);
        //
        //        List<Order> expiredOrders =
        // orderRepository.findByStatusAndCreatedAtBefore(OrderStatus.AWAITING_PAYMENT, threshold);
        //
        //        for (Order order : expiredOrders) {
        //            order.setStatus(OrderStatus.CANCELED);
        //
        //            for (OrderItem item : order.getItems()) {
        //                inventoryService.releaseReservation(item.getProduct(), item.getQuantity());
        //            }
        //        }
    }

    //    @Scheduled(fixedDelay = 5000)
    //    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void outboxPublisher() {
        log.info("Starting compensation service *** outboxPublisher");
        List<OutboxEvent> events = outboxRepository.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.NEW);
        log.info("events count is: {}", events.size());

        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send(event.getTopic(), event.getAggregateId().toString(), event.getPayload());
                event.setStatus(OutboxStatus.SENT);
                event.setProcessedAt(LocalDateTime.now());

            } catch (Exception ex) {
                log.error("Outbox publish failed. eventId: {}, orderId: {}", event.getId(), event.getAggregateId());
                event.setRetryCount(event.getRetryCount() + 1);

                log.info("event retry count is: {}", event.getRetryCount());
                if (event.getRetryCount() > 10) {
                    event.setStatus(OutboxStatus.FAILED);
                    orderPaymentService.compensateOrder(event);
                }
            }
        }
    }
}

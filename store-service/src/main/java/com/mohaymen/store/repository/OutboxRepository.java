package com.mohaymen.store.repository;

import com.mohaymen.store.model.OutboxEvent;
import com.mohaymen.store.model.OutboxStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Mehran
 * Created on: 7/13/26
 */
@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}

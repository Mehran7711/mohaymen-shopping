package com.mohaymen.store.service;

public interface ICompensateService {
    void releaseExpiredReservations();

    void outboxPublisher();
}

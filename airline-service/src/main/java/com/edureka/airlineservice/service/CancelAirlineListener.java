package com.edureka.airlineservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CancelAirlineListener {

    @JmsListener(destination = "cancel-airline-queue")
    public void listen(String message) {
        // cancel airline
        // cancel ticket and make it available to other users
    }
}

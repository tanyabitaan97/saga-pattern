package com.edureka.sagaorchestrator.service;

import com.edureka.sagaorchestrator.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

//@Slf4j
//@RequiredArgsConstructor
@Service
public class SagaJmsListener {
    private final JmsTemplate jmsTemplate;
    private final OrderService orderService;
    
    

    public SagaJmsListener(JmsTemplate jmsTemplate, OrderService orderService) {
		super();
		this.jmsTemplate = jmsTemplate;
		this.orderService = orderService;
	}



	@JmsListener(destination = "saga-queue")
    public void listen(Order order) {
        System.out.println("Message Consumed: " + order);
        orderService.processSagaResponse(order);
    }


}

package com.edureka.sagaorchestrator.service;

import com.edureka.sagaorchestrator.domain.*;
import com.edureka.sagaorchestrator.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
//@RequiredArgsConstructor
public class HotelAction implements Action<OrderState, OrderEvent> {
    private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;


    public HotelAction(JmsTemplate jmsTemplate, OrderRepository orderRepository) {
		super();
		this.jmsTemplate = jmsTemplate;
		this.orderRepository = orderRepository;
	}


	@Override
    public void execute(StateContext<OrderState, OrderEvent> context) {
        System.out.println("Hotel Action");
        String orderId = (String) context.getMessage().getHeaders().get("ORDER_ID_HEADER");
        // find from DB orderId
        Optional<Order> orderOptional = orderRepository.findById(Long.parseLong(orderId));
        jmsTemplate.convertAndSend("hotel-queue", orderOptional.get());
    }
}

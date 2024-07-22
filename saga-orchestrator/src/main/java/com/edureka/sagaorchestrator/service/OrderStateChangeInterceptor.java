package com.edureka.sagaorchestrator.service;

import com.edureka.sagaorchestrator.domain.Order;
import com.edureka.sagaorchestrator.domain.OrderEvent;
import com.edureka.sagaorchestrator.domain.OrderState;
import com.edureka.sagaorchestrator.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Slf4j
@Component
//@RequiredArgsConstructor
public class OrderStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderState, OrderEvent> {

    private final OrderRepository orderRepository;
    

    public OrderStateChangeInterceptor(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}


	/**
     * Detect state change(PRE) and persist state of an Order into DB
     * @param state
     * @param message
     * @param transition
     * @param stateMachine
     */
    @Override
    public void preStateChange(State<OrderState, OrderEvent> state, Message<OrderEvent> message, Transition<OrderState, OrderEvent> transition, StateMachine<OrderState, OrderEvent> stateMachine) {
        Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault("ORDER_ID_HEADER", "")))
                .ifPresent(orderId -> {
                    System.out.println("$$$$ Interceptor: orderId " + orderId + " $$$$");
                    System.out.println("Saving state for order id: " + orderId + " Status: " + state.getId());
//                    orderRepository.getOne(UUID.fromString(orderId));// todo: may not be correct UUID
                    Order order = orderRepository.getOne(Long.parseLong(orderId));// todo: may not be correct UUID
                    order.setOrderState(state.getId());
                    orderRepository.saveAndFlush(order);// Hibernate is LazyLoading, so to avoid that.
                });
    }


}

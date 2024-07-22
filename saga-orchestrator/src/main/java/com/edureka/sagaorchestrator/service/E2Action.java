package com.edureka.sagaorchestrator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// message sent to JMS
public class E2Action implements Action<String, String> {
    /*private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;*/


    @Override
    public void execute(StateContext<String, String> context) {
//        String orderId = (String) context.getMessage().getHeaders().get(OrderManagerImpl.ORDER_ID_HEADER);
//        // find from DB orderId
//        Optional<Order> orderOptional = orderRepository.findById(Long.parseLong(orderId));
//        jmsTemplate.convertAndSend("allocate-queue", AllocateOrderRequest.builder().order(orderOptional.get()));

        System.out.println("2222222222");
        System.out.println("E-2 Action");
    }
}

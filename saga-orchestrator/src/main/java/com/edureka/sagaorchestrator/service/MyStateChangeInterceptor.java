package com.edureka.sagaorchestrator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MyStateChangeInterceptor extends StateMachineInterceptorAdapter<String, String> {

    @Override
    public void preStateChange(State<String, String> state, Message<String> message, Transition<String, String> transition, StateMachine<String, String> stateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            /*Optional.ofNullable(Long.class.cast(msg.getHeaders ().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
                    .ifPresent(paymentId -> {
                        Payment payment = paymentRepository.getOne(paymentId);
                        payment.setState(state.getId());
                        paymentRepository.save(payment);

                    });*/
            ;
            System.out.println("INSIDE MyStateChangeInterceptor");
        });
    }
}

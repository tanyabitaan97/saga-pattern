/*
package com.edureka.sagaorchestrator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyStateService {
    private final StateMachineFactory<String, String> myStateMachineFactory;
    private final MyStateChangeInterceptor myStateChangeInterceptor;

    public void initiateStateE1() {
        System.out.println("INITIATING E1");
        sendOrderEvent("Begin-E1", "E1");
    }

    public void stateE2() {
        System.out.println("INITIATING E2");
        sendOrderEvent("Begin-E2", "E2");
    }

    public void stateE3() {
        System.out.println("INITIATING E3");
        sendOrderEvent("Begin-E3", "E3");
    }

    private void sendOrderEvent(String message, String event) {
        System.out.println("SEND ORDER EVENT: " + event);
        System.out.println("MESSAGE: " + message);
        StateMachine<String, String> sm = build(message);
        Message<String> msg = MessageBuilder.withPayload(event)
                .setHeader("my-header", "my-header-value")
//                .setHeader(ORDER_ID_HEADER, order.getOrderId().toString())
                .build();

        sm.sendEvent(msg);
    }

    private StateMachine<String, String> build(String message) {
        System.out.println("BUILD STATE MACHINE");
        // todo: is it good to have state-machine based on orderId?
        StateMachine<String, String> sm = myStateMachineFactory.getStateMachine(*/
/*order.getOrderId()*//*
);
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(myStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>("status-new", null, null, null));
                });

        sm.start();
        return sm;
    }
}
*/

package com.edureka.sagaorchestrator.service;

import com.edureka.sagaorchestrator.domain.Order;
import com.edureka.sagaorchestrator.domain.OrderEvent;
import com.edureka.sagaorchestrator.domain.OrderState;
import com.edureka.sagaorchestrator.domain.OrderStatus;
import com.edureka.sagaorchestrator.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//@Slf4j
@Service
//@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StateMachineFactory<OrderState, OrderEvent> stateMachineFactory;
    private final OrderStateChangeInterceptor orderStateChangeInterceptor;
    
    

    public OrderService(OrderRepository orderRepository,
			StateMachineFactory<OrderState, OrderEvent> stateMachineFactory,
			OrderStateChangeInterceptor orderStateChangeInterceptor) {
		super();
		this.orderRepository = orderRepository;
		this.stateMachineFactory = stateMachineFactory;
		this.orderStateChangeInterceptor = orderStateChangeInterceptor;
	}

	/**
     * places new order
     * @param order
     * @return
     */
    @Transactional
    public Order placeOrder(Order order) {
        order.setOrderId(null); // defensive approach for new object
        order.setOrderState(OrderState.NEW);
        order.setOrderStatus(OrderStatus.NEW);
        Order savedOrder = orderRepository.saveAndFlush(order);
        System.out.println("$$$$ orderId " + order.getOrderId() + " $$$$");

        sendOrderEvent(order, OrderEvent.BOOK_AIRLINE);
        return savedOrder;
    }

    @Transactional
    public void processSagaResponse(Order order) {
        if(order.getOrderStatus().name().equals("AIRLINE_SUCCESS")) {
            System.out.println("Airline Booking is succesful");
            order.setOrderState(OrderState.AIRLINE);
//            order.setOrderStatus(OrderStatus.NEW);
            Order savedOrder = orderRepository.saveAndFlush(order);
            System.out.println("sending event: BOOK_AIRLINE_COMPLETED");
            sendOrderEvent(order, OrderEvent.BOOK_AIRLINE_COMPLETED);
        } else if(order.getOrderStatus().name().equals("HOTEL_SUCCESS")) {
            System.out.println("Hotel Booking is successful");
            order.setOrderState(OrderState.COMPLETED);
            order.setOrderStatus(OrderStatus.COMPLETED);
            Order savedOrder = orderRepository.saveAndFlush(order);
            System.out.println("$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$$$$$$");
            System.out.println("Order Placed successfully");
            System.out.println("Inform User");
            System.out.println("$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$$$$$$");
        } else if(order.getOrderStatus().name().equals("HOTEL_FAILED")) {
            System.out.println("Hotel Booking is failed");
            order.setOrderState(OrderState.HOTEL);
//            order.setOrderStatus(OrderStatus.FAILED);
            Order savedOrder = orderRepository.saveAndFlush(order);
            System.out.println("sending event: BOOK_HOTEL_FAILED");
            sendOrderEvent(order, OrderEvent.BOOK_HOTEL_FAILED);
        } else if(order.getOrderStatus().name().equals("FAILED")) {
            System.out.println("******************");
            System.out.println("******************");
            System.out.println("******************");
            System.out.println("BOOKING FAILED");
            System.out.println("******************");
            System.out.println("******************");
            System.out.println("******************");
        }
    }
    public Order processAirlineResponse(Order order) {
        if (order.getOrderStatus().equals(OrderStatus.AIRLINE_SUCCESS)) {

        }
        return order;
    }

    public Order orderHotel(Order order) {
        Order savedOrder = orderRepository.saveAndFlush(order);

        sendOrderEvent(order, OrderEvent.BOOK_HOTEL);
        return savedOrder;
    }

    // method -> processValidationResult ->
    //                  sendOrderEvent(Success) or sendOrderEvent(failure)

    /**
     * Sends event to state machine which triggers state change
     * @param order
     * @param bookingEvent
     */
    private void sendOrderEvent(Order order, OrderEvent bookingEvent) {
        StateMachine<OrderState, OrderEvent> sm = build(order);
        System.out.println("$$$$ orderId " + order.getOrderId() + " $$$$");
        System.out.println("********************");
        System.out.println("state-machine built as: " + sm + " - sm.getId(): " + sm.getId());
        System.out.println("********************");
        Message<OrderEvent> msg = MessageBuilder.withPayload(bookingEvent)
                .setHeader("ORDER_ID_HEADER", order.getOrderId().toString())
                .build();

        sm.sendEvent(msg);
    }

    /**
     * Returns an instance of a stat-machine. It instantiates a new instance of state-machine
     * if it does not find a state-machine associated with the provided orderId.
     * @param order
     * @return
     */
    private StateMachine<OrderState, OrderEvent> build(Order order) {
        // request state-machine-factory to get instance of state-machine
        // spring is going to do some caching. So if a state-machine already exists with the provided
//        StateMachine<OrderState, OrderEvent> sm = stateMachineFactory.getStateMachine();
        System.out.println("$$$$ orderId " + order.getOrderId() + " $$$$");
        System.out.println("$$$$ String.valueOf(order.getOrderId()) " + order.getOrderId() + " $$$$");
        // https://docs.spring.io/spring-statemachine/docs/current/api/
        // Build a new StateMachine instance with a given machine id.
        StateMachine<OrderState, OrderEvent> sm = stateMachineFactory.getStateMachine(String.valueOf(order.getOrderId()));

        sm.stop(); // stop state-machine - only one state machine belonging to an orderId
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    // interceptor intercepts every state change
                    sma.addStateMachineInterceptor(orderStateChangeInterceptor);
                    // specific status to be passed in state-machine-context
                    sma.resetStateMachine(new DefaultStateMachineContext<>(order.getOrderState(), null, null, null));

                });

        sm.start();
        return sm;
    }
}

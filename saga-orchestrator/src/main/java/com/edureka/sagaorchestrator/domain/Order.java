package com.edureka.sagaorchestrator.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "order_table")
/*@Data
@Builder
@ToString*/
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@ToString
public class Order implements Serializable {
	
	
    public Order() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private OrderState orderState;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String sourceLocation;
    private String destinationLocation;

    private BigDecimal amount;

    private LocalDate bookingDate;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrderState getOrderState() {
		return orderState;
	}

	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
    
    
}

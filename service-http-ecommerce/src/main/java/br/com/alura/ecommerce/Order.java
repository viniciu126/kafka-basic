package br.com.alura.ecommerce;

import java.math.BigDecimal;

public class Order {
    private final String orderId;
    private final BigDecimal amount;
    private final String email;

    public Order(String orderId, BigDecimal value, String email) {
        this.orderId = orderId;
        this.amount = value;
        this.email = email;
    }

    public String getId() {
        return orderId;
    }
}

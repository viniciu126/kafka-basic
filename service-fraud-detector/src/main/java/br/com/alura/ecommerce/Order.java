package br.com.alura.ecommerce;

import java.math.BigDecimal;

public class Order {
    private final String orderId, email;
    private final BigDecimal amount;

    public Order(String orderId, BigDecimal value, String email) {
        this.orderId = orderId;
        this.amount = value;
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }



    @Override
    public String toString() {
        return "Order{" +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getEmail() {
        return this.email;
    }

    public String getId() {
        return orderId;
    }
}

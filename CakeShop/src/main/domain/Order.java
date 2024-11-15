package main.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order<ID> implements Identifiable<ID>, Serializable {
    private ID orderId;
    private final ID cakeId;
    private final String customerName;
    private final Integer quantity;
    private String status;
    private LocalDateTime createdAt;

    public static final String PENDING = "Pending";
    public static final String FINISHED = "Finished";
    public static final String CANCELLED = "Cancelled";

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Order(ID cakeId, String customerName, Integer quantity) {
        this.cakeId = cakeId;
        this.customerName = customerName;
        this.quantity = quantity;
        this.status = PENDING;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public ID getId() {
        return orderId;
    }

    @Override
    public void setId(ID orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ID getCakeId() {
        return cakeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return orderId + "," + cakeId + "," + customerName + "," + quantity + "," + status + "," + createdAt.format(DATE_TIME_FORMATTER);
    }
}

package main.repository.file.text;

import main.domain.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderTextFileRepository<ID> extends TextFileRepository<ID, Order<ID>> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OrderTextFileRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected Order<ID> parseEntity(String line) {
        String[] fields = line.split(",");

        ID orderId = (ID) Integer.valueOf(fields[0].trim());
        ID cakeId = (ID) Integer.valueOf(fields[1].trim());
        String customerName = fields[2].trim();
        Integer quantity = Integer.parseInt(fields[3].trim());
        String status = fields[4].trim();
        LocalDateTime createdAt = LocalDateTime.parse(fields[5].trim(), DATE_TIME_FORMATTER);

        Order<ID> order = new Order<>(cakeId, customerName, quantity);
        order.setId(orderId);
        order.setStatus(status);
        order.setCreatedAt(createdAt);

        return order;
    }
}

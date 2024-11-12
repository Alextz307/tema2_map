package main.repository.file.binary;

import main.domain.Order;

public class OrderBinaryFileRepository<ID> extends BinaryFileRepository<ID, Order<ID>> {
    public OrderBinaryFileRepository(String filename) {
        super(filename);
    }
}
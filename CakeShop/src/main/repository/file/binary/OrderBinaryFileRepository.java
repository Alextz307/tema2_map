package main.repository.file.binary;

import main.domain.Order;

public class OrderBinaryFileRepository<Integer> extends BinaryFileRepository<Integer, Order<Integer>> {
    public OrderBinaryFileRepository(String filename) {
        super(filename);
    }
}

package main.repository.file.binary;

import main.domain.BirthdayCake;

public class BirthdayCakeBinaryFileRepository<Integer> extends BinaryFileRepository<Integer, BirthdayCake<Integer>> {
    public BirthdayCakeBinaryFileRepository(String filename) {
        super(filename);
    }
}

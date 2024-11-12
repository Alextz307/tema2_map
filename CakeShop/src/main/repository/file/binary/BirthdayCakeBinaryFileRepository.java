package main.repository.file.binary;

import main.domain.BirthdayCake;

public class BirthdayCakeBinaryFileRepository<ID> extends BinaryFileRepository<ID, BirthdayCake<ID>> {
    public BirthdayCakeBinaryFileRepository(String filename) {
        super(filename);
    }
}
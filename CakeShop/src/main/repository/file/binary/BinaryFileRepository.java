package main.repository.file.binary;

import main.domain.Identifiable;
import main.repository.file.FileRepository;

import java.io.*;

public class BinaryFileRepository<ID, T extends Identifiable<ID>> extends FileRepository<ID, T> {
    public BinaryFileRepository(String filename) {
        super(filename);
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating file: " + filename, e);
        }
    }

    @Override
    protected void readFromFile() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                T entity = (T) input.readObject();
                super.add(entity);
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading from binary file: " + filename, e);
        }
    }

    @Override
    protected void writeToFile() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (T entity : findAll()) {
                output.writeObject(entity);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to binary file: " + filename, e);
        }
    }
}

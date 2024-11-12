package main.repository.file.text;

import main.domain.BirthdayCake;

public class BirthdayCakeTextFileRepository<ID> extends TextFileRepository<ID, BirthdayCake<ID>> {
    public BirthdayCakeTextFileRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected BirthdayCake<ID> parseEntity(String line) {
        String[] fields = line.split(",");

        ID id = (ID) Integer.valueOf(fields[0].trim());

        String name = fields[1].trim();
        String flavor = fields[2].trim();
        double price = Double.parseDouble(fields[3].trim());
        int layers = Integer.parseInt(fields[4].trim());

        return new BirthdayCake<>(id, name, flavor, price, layers);
    }
}

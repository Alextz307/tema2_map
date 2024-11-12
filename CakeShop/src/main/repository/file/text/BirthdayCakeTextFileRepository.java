package main.repository.file.text;

import main.domain.BirthdayCake;

public class BirthdayCakeTextFileRepository extends TextFileRepository<Integer, BirthdayCake<Integer>> {
    public BirthdayCakeTextFileRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected BirthdayCake<Integer> parseEntity(String line) {
        String[] fields = line.split(",");

        Integer id = Integer.valueOf(fields[0].trim());

        String name = fields[1].trim();
        String flavor = fields[2].trim();
        double price = Double.parseDouble(fields[3].trim());
        int layers = Integer.parseInt(fields[4].trim());

        return new BirthdayCake<>(id, name, flavor, price, layers);
    }
}

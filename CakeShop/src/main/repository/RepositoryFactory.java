package main.repository;

import main.domain.BirthdayCake;
import main.domain.Order;
import main.repository.database.BirthdayCakeDbRepository;
import main.repository.database.OrderDbRepository;
import main.repository.file.binary.BirthdayCakeBinaryFileRepository;
import main.repository.file.binary.OrderBinaryFileRepository;
import main.repository.file.text.BirthdayCakeTextFileRepository;
import main.repository.file.text.OrderTextFileRepository;
import main.repository.memory.InMemoryBirthdayCakeRepository;
import main.repository.memory.InMemoryOrderRepository;

public class RepositoryFactory {
    public static IRepository<Integer, BirthdayCake<Integer>> createCakeRepository(String repositoryType, String fileNameOrUrl) {
        return switch (repositoryType.toLowerCase()) {
            case "binary" -> new BirthdayCakeBinaryFileRepository<>(fileNameOrUrl);
            case "text" -> new BirthdayCakeTextFileRepository(fileNameOrUrl);
            case "database" -> new BirthdayCakeDbRepository(fileNameOrUrl);
            default -> new InMemoryBirthdayCakeRepository();
        };
    }

    public static IRepository<Integer, Order<Integer>> createOrderRepository(String repositoryType, String fileNameOrUrl) {
        return switch (repositoryType.toLowerCase()) {
            case "binary" -> new OrderBinaryFileRepository<>(fileNameOrUrl);
            case "text" -> new OrderTextFileRepository(fileNameOrUrl);
            case "database" -> new OrderDbRepository(fileNameOrUrl);
            default -> new InMemoryOrderRepository();
        };
    }
}

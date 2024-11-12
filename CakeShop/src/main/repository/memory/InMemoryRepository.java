package main.repository.memory;

import main.domain.Identifiable;
import main.repository.IRepository;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    private final HashMap<ID, T> items = new HashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public ID add(T item) {
        Long id = currentId.getAndIncrement();
        item.setId((ID) id);
        items.put(item.getId(), item);

        return (ID) id;
    }

    @Override
    public Iterable<T> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public void modify(T updatedItem) {
        items.put(updatedItem.getId(), updatedItem);
    }

    @Override
    public void delete(ID id) {
        items.remove(id);
    }
}

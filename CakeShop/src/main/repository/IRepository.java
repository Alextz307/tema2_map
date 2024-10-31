package main.repository;

import main.domain.BirthdayCake;
import main.domain.Identifiable;

import java.util.List;
import java.util.Optional;

public interface IRepository<ID, T extends Identifiable<ID>> {
    ID add(T item);

    Iterable<T> findAll();

    Optional<T> findById(ID id);

    void modify(T updatedItem);

    void delete(ID id);
}

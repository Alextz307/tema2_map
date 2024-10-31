package main.repository;

import main.domain.Identifiable;

public class InMemoryBirthdayCakeRepository<ID, T extends Identifiable<ID>> extends InMemoryRepository<ID, T> {
}

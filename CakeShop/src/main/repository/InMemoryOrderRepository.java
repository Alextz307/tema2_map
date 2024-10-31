package main.repository;

import main.domain.Identifiable;

public class InMemoryOrderRepository<ID, T extends Identifiable<ID>> extends InMemoryRepository<ID, T> {
}

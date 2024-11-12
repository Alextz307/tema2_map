package main.validators;

import main.domain.BirthdayCake;

public class CakeValidator<ID> {
    public void validate(BirthdayCake<ID> cake) {
        validateCake(cake);
    }

    private void validateCake(BirthdayCake<ID> cake) {
        if (cake.getName() == null || cake.getName().isEmpty()) {
            throw new IllegalArgumentException("Cake name cannot be empty.");
        }

        if (cake.getFlavor() == null || cake.getFlavor().isEmpty()) {
            throw new IllegalArgumentException("Cake flavor cannot be empty.");
        }

        if (cake.getPrice() <= 0) {
            throw new IllegalArgumentException("Cake price must be positive.");
        }

        if (cake.getLayers() <= 0) {
            throw new IllegalArgumentException("Cake must have at least 1 layer.");
        }
    }
}

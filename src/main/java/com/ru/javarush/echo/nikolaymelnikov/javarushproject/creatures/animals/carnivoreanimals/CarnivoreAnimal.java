package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.carnivoreanimals;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.Animal;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Cell;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.Randomizer;

import java.util.Comparator;
import java.util.List;


public abstract class CarnivoreAnimal extends Animal {


    public CarnivoreAnimal(Coordinates position, Island island) {
        super(position, island);
    }

    public CarnivoreAnimal(int x, int y, Island island) {
        super(x, y, island);
    }

    @Override
    public void moveTo(Cell newCell) {
        super.moveTo(newCell);
        reduceEnergy();
    }
    @Override
    public void eat() {
        List<Animal> accessibleAnimals = cell.getFauna().stream()
                .filter(e -> this.getLuck().get(e.getName()) > 0)
                .toList();
        if (!accessibleAnimals.isEmpty()) {
            Animal victim = chooseVictim(accessibleAnimals);
            tryToEat(victim);
        } else {
            initializeAccessibleCells();
            moveTo(choosingDirectionForEat());
        }
        reduceEnergy();
    }
    public Cell choosingDirectionForEat() {
            return getAccessibleCells().stream()
                    .max(Comparator.comparing(Cell::getHerbivoreAnimalsQuantity))
                    .orElse(accessibleCells.get(Randomizer.randomize(0, accessibleCells.size())));
        }
}


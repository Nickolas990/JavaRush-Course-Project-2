package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.herbivoreanimals;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.Animal;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.grass.Plant;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Cell;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.Randomizer;

import java.util.Comparator;
import java.util.List;

public abstract class HerbivoreAnimal extends Animal {
    public HerbivoreAnimal(Coordinates position, Island island) {
        super(position, island);
    }

    public HerbivoreAnimal(int x, int y, Island island) {
        super(x, y, island);
    }

    @Override
    public void eat() {
        if (cell.getPlantsQuantity() > 0) {
            List<Animal> accessibleAnimals = cell.getFauna().stream()
                    .filter(e -> this.getLuck().get(e.getName()) > 0)
                    .toList();
            if (!accessibleAnimals.isEmpty()) {
                Animal victim = chooseVictim(accessibleAnimals);
                tryToEat(victim);
            } else  if (!cell.getFlora().isEmpty()){
                Plant plant = cell.getFlora().stream().findAny().orElse(cell.getFlora().get(0));
                plant.die();
                currentHanger += Island.getInstance().getSettings().getPlantsNutritionalValue();
                if (currentHanger > maxHunger) {
                    currentHanger = maxHunger;
                }
            }
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

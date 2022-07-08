package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.annotations.MaxCapacity;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.Creature;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.interfaces.Breeding;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.interfaces.Eating;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.interfaces.Moving;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Cell;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.settings.AnimalCharacteristics;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.settings.LuckTable;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.Randomizer;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public abstract class Animal extends Creature implements Moving, Eating, Breeding {


    protected int starve;
    protected List<Cell> accessibleCells = new ArrayList<>();

    protected AnimalCharacteristics[] animalCharacteristics;


    public Animal(Coordinates position, Island island) {
        super(position, island);
        settings = island.getSettings();
        starve = settings.getStarve();
        animalCharacteristics = island.getAnimalCharacteristics();
    }

    public Animal(int x, int y, Island island) {
        super(new Coordinates(x, y), island);
        settings = island.getSettings();
        starve = settings.getStarve();
        animalCharacteristics = island.getAnimalCharacteristics();
    }

    public abstract Cell choosingDirectionForEat();

    @Override
    public void moveTo(Cell newCell) {
        reduceEnergy();
        leaveCell();
        newCell.addAnimalInCell(this);
        setPosition(newCell.getCoordinates());
    }


    @Override
    public void breed() {
        List<Animal> breeders = chooseForBreed();
        if (!breeders.isEmpty()) {
            Animal parentAnimal = breeders.get(Randomizer.randomize(0, breeders.size()));
            bornNewAnimal(parentAnimal);
            parentAnimal.reduceEnergy();
            this.reduceEnergy();
        } else {
            initializeAccessibleCells();
            moveTo(choosingDirectionForBreed());
        }
    }


    public Cell choosingDirectionForBreed() {
        return getAccessibleCells().stream()
                .filter(e -> e.getCurrentCapacityOfCell()
                        .containsKey(getName()))
                .findFirst()
                .orElse(accessibleCells.get(Randomizer.randomize(0, accessibleCells.size())));

    }

    public List<Animal> chooseForBreed() {
        Cell cell = island.getCell(getPosition());
        return cell.getFauna()
                .stream()
                .filter(e -> e.getName().equals(getName())
                && !(e.equals(this)) && e.getCurrentEnergy().get() > 0 && e.currentHanger > e.maxHunger / 2)
                .toList();
    }

    public Animal chooseVictim(List<Animal> accessibleAnimals) {

        return accessibleAnimals.stream()
                .max(Comparator.comparing(Creature::getWeight))
                .orElse(accessibleAnimals.get(Randomizer.randomize(0, accessibleAnimals.size())));
    }

    public void tryToEat(Animal victim) {
        Double luck = getLuck().get(victim.getName());
        if (Randomizer.randomize(0, 100) < luck) {
            this.setCurrentHanger(getCurrentHanger() + victim.getWeight());
            this.setStarve(settings.getStarve());
            if (this.getCurrentHanger() > this.getMaxHunger()) {
                this.setCurrentHanger(getMaxHunger());
            }
            victim.die();
        }
    }

    protected void initializeAccessibleCells() {
        accessibleCells.clear();
        Coordinates coordinates = getPosition();

        if (coordinates.getX() - 1 >= 0) {
            accessibleCells.add(island.getCell(coordinates.getX() - 1, coordinates.getY()));
        }
        if (coordinates.getY() - 1 >= 0) {
            accessibleCells.add(island.getCell(coordinates.getX(), coordinates.getY() - 1));
        }
        if ((coordinates.getX() + 1) < island.getWight()) {
            accessibleCells.add(island.getCell(coordinates.getX() + 1, coordinates.getY()));
        }
        if (coordinates.getY() + 1 < island.getHeight()) {
            accessibleCells.add(island.getCell(coordinates.getX(), coordinates.getY() + 1));
        }
    }

    @Override
    public void leaveCell() {
        Cell cell = island.getCell(getPosition());
        cell.getFauna().remove(this);
        cell.getCurrentCapacityOfCell().merge(getName(), 1, Integer::sum);

        if (cell.getCurrentCapacityOfCell().get(getName()) >= getClass().getAnnotation(MaxCapacity.class).value()) {
            cell.getCurrentCapacityOfCell().remove(getName());
        }
        cell.removeThis(this);
    }

    public void init() {
        for (AnimalCharacteristics animal : animalCharacteristics) {
            if (animal.getName().equals(getName())) {
                weight = animal.getWeight();
                maxEnergy = animal.getMaxEnergy();
                maxHunger = animal.getMaxHunger();
                currentEnergy.set(animal.getCurrentEnergy());
                currentHanger = animal.getCurrentHunger();
                luck = LuckTable.getLuckTable().get(animal.getName());
                maxCapacity = animal.getMaxCapacity();
                emoji = animal.getEmoji();
                break;
            }
        }
    }

    public void getThisPosition(Coordinates coordinates) {
        setCell(island.getCell(coordinates));
        cell.addAnimalInCell(this);
    }

    private void bornNewAnimal(Animal animal) {

        try {
            Animal newAnimal = this.getClass().getConstructor(Coordinates.class, Island.class).newInstance(getPosition(), island);
            newAnimal.getThisPosition(animal.getPosition());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 InstantiationException e) {
            System.err.println(e);
            throw new RuntimeException(e + " Problem with newBornAnimal");
        }
    }
}

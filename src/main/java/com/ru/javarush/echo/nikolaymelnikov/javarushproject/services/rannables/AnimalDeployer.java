package com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.rannables;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.annotations.MaxCapacity;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.Creature;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.Animal;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.CoordinatesCreator;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.Randomizer;

public class AnimalDeployer implements Runnable {

    private final Class<? extends Creature> creatureClass;
    private final Island island;

    public AnimalDeployer(Class<? extends Creature> clazz, Island island) {
        this.creatureClass = clazz;
        this.island = island;
    }

    @Override
    public void run()  {

        try {
            MaxCapacity capacityAnnotation = creatureClass.getAnnotation(MaxCapacity.class);
            int quantity = Randomizer.randomize(0, capacityAnnotation.value() * island.getWight() * island.getHeight());
            for (int i = 0; i <= quantity; i++) {
                Animal animal = (Animal) creatureClass.getConstructor(Coordinates.class, Island.class)
                        .newInstance(CoordinatesCreator.generateCoordinates(), island);
                animal.getThisPosition(animal.getPosition());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

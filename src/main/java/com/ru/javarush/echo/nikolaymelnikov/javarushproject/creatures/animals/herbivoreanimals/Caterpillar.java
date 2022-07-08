package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.herbivoreanimals;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.annotations.MaxCapacity;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;

@MaxCapacity(1000)
public class Caterpillar extends HerbivoreAnimal {

    public Caterpillar(Coordinates position, Island island) {
        super(position, island);
        init();
    }
}

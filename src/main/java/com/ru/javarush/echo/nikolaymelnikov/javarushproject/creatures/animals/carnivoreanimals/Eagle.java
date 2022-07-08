package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.carnivoreanimals;


import com.ru.javarush.echo.nikolaymelnikov.javarushproject.annotations.MaxCapacity;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;

@MaxCapacity(20)
public class Eagle extends CarnivoreAnimal {

    public Eagle(Coordinates position, Island island) {
        super(position, island);
        init();
    }
}

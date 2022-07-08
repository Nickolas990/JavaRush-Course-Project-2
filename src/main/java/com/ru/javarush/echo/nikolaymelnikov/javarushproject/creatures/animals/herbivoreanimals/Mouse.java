package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.herbivoreanimals;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.annotations.MaxCapacity;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;


@MaxCapacity(500)
public class Mouse extends HerbivoreAnimal {


    public Mouse(Coordinates position, Island island) {
        super(position, island);
        init();
    }
}

package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.carnivoreanimals;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.annotations.MaxCapacity;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MaxCapacity(5)
public class Bear extends CarnivoreAnimal {


    public Bear(Coordinates position, Island island) {
        super(position, island);
        init();
    }
}

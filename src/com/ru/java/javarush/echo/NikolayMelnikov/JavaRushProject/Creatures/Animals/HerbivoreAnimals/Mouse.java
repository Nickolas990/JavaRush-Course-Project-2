package com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Creatures.Animals.HerbivoreAnimals;

import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Annotations.LuckNumber;
import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Annotations.MaxCapacity;
import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Island.Coordinates;

import java.util.concurrent.atomic.AtomicInteger;


@MaxCapacity(500)
@LuckNumber(8)
public class Mouse extends HerbivoreAnimal {


    public Mouse(int x, int y) {
        super(x, y);
    }

    public Mouse(Coordinates position) {
        super(position);
    }

    {
        name = "Мышь";
        weight = 0.05;
        maxEnergy = 1;
        maxHunger = 0.01;
        currentEnergy = new AtomicInteger(maxEnergy);
        currentHanger = maxHunger;
    }
}

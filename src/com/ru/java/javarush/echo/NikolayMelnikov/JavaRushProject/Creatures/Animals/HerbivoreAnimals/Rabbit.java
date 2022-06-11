package com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Creatures.Animals.HerbivoreAnimals;

import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Annotations.LuckNumber;
import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Annotations.MaxCapacity;
import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Island.Coordinates;

@MaxCapacity(150)
@LuckNumber(7)
public class Rabbit extends HerbivoreAnimal {

    public Rabbit(int x, int y) {
        super(x, y);
    }

    public Rabbit(Coordinates position) {
        super(position);
    }

    {
        name = "Кролик";
        weight = 2;
        maxEnergy = 2;
        maxHunger = 0.45;
        currentEnergy = maxEnergy;
        currentHanger = maxHunger;
    }
}

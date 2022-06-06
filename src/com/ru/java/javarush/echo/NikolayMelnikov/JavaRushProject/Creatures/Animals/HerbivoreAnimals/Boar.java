package com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Creatures.Animals.HerbivoreAnimals;

import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Annotations.MaxCapacity;
import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Island.Coordinates;

@MaxCapacity(50)
public class Boar extends HerbivoreAnimal {


    public Boar(int x, int y) {
        super(x, y);
    }

    public Boar(Coordinates position) {
        super(position);
    }

    {
        name = "�����";
        weight = 400;
        energy = 2;
        hanger = 50;
    }
}
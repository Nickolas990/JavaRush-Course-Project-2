package com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Creatures.Animals.CarnivoreAnimals;


import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Annotations.MaxCapacity;
import com.ru.java.javarush.echo.NikolayMelnikov.JavaRushProject.Island.Coordinates;

@MaxCapacity(30)
public class Boa extends CarnivoreAnimal{

    public Boa(int x, int y) {
        super(x, y);
    }

    public Boa(Coordinates position) {
        super(position);
    }

    {
        name = "����";
        weight = 15;
        energy = 1;
        hanger = 3;
    }
}
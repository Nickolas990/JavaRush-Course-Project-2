package com.ru.javarush.echo.nikolaymelnikov.javarushproject.util;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;

public class CoordinatesCreator {
    private static final Island island = Island.getInstance();

    public static Coordinates generateCoordinates() {
        int generatedX = Randomizer.randomize(0, island.getWight());
        int generatedY = Randomizer.randomize(0, island.getHeight());
        return new Coordinates(generatedX, generatedY);
    }
}

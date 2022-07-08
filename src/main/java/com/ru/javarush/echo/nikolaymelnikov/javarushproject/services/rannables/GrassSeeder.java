package com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.rannables;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.grass.Plant;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Cell;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.Randomizer;

import java.time.LocalTime;

public class GrassSeeder implements Runnable {
    private final String START_MESSAGE = "Planting grass...";
    Island island;

    public GrassSeeder(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        System.out.println(LocalTime.now() + " " + START_MESSAGE);
        for (int x = 0; x < island.getWight(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Cell cell = island.getCell(x, y);
                if (cell.getFlora().size() < 200) {
                    int targetQuantityOfGrass = Randomizer.randomize(0, 200);
                    if (cell.getFlora().size() < targetQuantityOfGrass) {
                        for (int k = 0; k < targetQuantityOfGrass; k++) {
                            cell.addPlantInCell(new Plant(cell.getCoordinates(), island));
                        }
                    }
                }
            }
        }
    }
}

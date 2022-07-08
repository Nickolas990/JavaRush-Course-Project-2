package com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.executors;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.Creature;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NewDay {
    public static final String STARTING_NEW_DAY_MESSAGE = "Starting new day...";
    public static final String RESTORE_ENERGY_ERROR_MESSAGE = "Animals did not rest successfully";
    public static final String NEW_DAY_MESSAGE = "Animals are ready to the new day";
    private ExecutorService service = Executors.newFixedThreadPool(10);;


    public void startNewDay() {
        Thread.currentThread().setName("NewDayStarter");
        System.out.println(STARTING_NEW_DAY_MESSAGE);
        service.submit(() -> Arrays.stream(Island.getInstance().getIslandField())
                .forEach(cell -> Arrays
                        .stream(cell)
                        .forEach(animal -> animal.getFauna()
                                .forEach(Creature::restoreEnergy))));
        try {
            if (service.awaitTermination(3, TimeUnit.SECONDS)) {
                System.out.println(RESTORE_ENERGY_ERROR_MESSAGE);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e + "Energy restoring speed too low");
        }

        try {
            service.awaitTermination(5, TimeUnit.SECONDS);
            service.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e + "Day was interrupted");
        }
        System.out.println(NEW_DAY_MESSAGE);

    }
}


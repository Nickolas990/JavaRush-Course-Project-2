package com.ru.javarush.echo.nikolaymelnikov.javarushproject.services;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.executors.FaunaImmigrator;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.executors.NewDay;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.rannables.ActingOfTheWorld;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.rannables.GrassSeeder;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.ExecutorsAwaitingConstants;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.GrassThreadFactory;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Runner {
    public static final int DAY_TIME_IN_MILLIS = 4000;
    Island island;
    private int day = 0;
    private final String IF_EXIT_QUESTION = "Type \"exit\" to end the program or any key to start new day";
    private final String NEW_DAY = "Current day: ";
    Scanner scanner = new Scanner(System.in);
    private ScheduledExecutorService grass = Executors.newScheduledThreadPool(1, new GrassThreadFactory());

    public Runner(Island island) {
        this.island = island;
    }

    public void startSimulation() {
        new FaunaImmigrator(island).immigration();
        grass.scheduleAtFixedRate(new GrassSeeder(island), 0, 1, TimeUnit.MINUTES);
        while (true) {
            System.out.println(NEW_DAY + ++day);

            beginNextDay();
            try {
                Thread.sleep(DAY_TIME_IN_MILLIS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(IF_EXIT_QUESTION);
            String answer = scanner.nextLine();
            if ("exit".equalsIgnoreCase(answer)) {
                System.out.println("Entered exit");
                break;
            } else {
                new NewDay().startNewDay();
            }
        }
        try {
            grass.shutdown();
            grass.awaitTermination(ExecutorsAwaitingConstants.GRASS_SEEDER_AWAITING, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Cycle Interrupted");

    }

    private void beginNextDay() {
        Thread actOfWorld = new Thread(new ActingOfTheWorld(island));
        actOfWorld.setName("World Act");
        actOfWorld.start();
    }
}

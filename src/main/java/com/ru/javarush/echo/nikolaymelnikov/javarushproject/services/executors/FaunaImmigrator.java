package com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.executors;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.carnivoreanimals.*;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.herbivoreanimals.*;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.rannables.AnimalDeployer;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.ExecutorsAwaitingConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class FaunaImmigrator {
    Island island;



    public static final String ANIMAL_DEPLOYING_MESSAGE = "Waiting for animal deploying";
    public static final String END_OF_DEPLOYING_MESSAGE = "Animal deployed";
    private List<Runnable> tasks = new ArrayList<>();

    public FaunaImmigrator(Island island) {
        this.island = island;
    }
    public  void immigration() {

        tasks.add(new AnimalDeployer(Bear.class, island));
        tasks.add(new AnimalDeployer(Boa.class, island));
        tasks.add(new AnimalDeployer(Eagle.class, island));
        tasks.add(new AnimalDeployer(Fox.class, island));
        tasks.add(new AnimalDeployer(Wolf.class, island));
        tasks.add(new AnimalDeployer(Boar.class, island));
        tasks.add(new AnimalDeployer(Buffalo.class, island));
        tasks.add(new AnimalDeployer(Caterpillar.class, island));
        tasks.add(new AnimalDeployer(Deer.class, island));
        tasks.add(new AnimalDeployer(Duck.class, island));
        tasks.add(new AnimalDeployer(Goat.class, island));
        tasks.add(new AnimalDeployer(Horse.class, island));
        tasks.add(new AnimalDeployer(Mouse.class, island));
        tasks.add(new AnimalDeployer(Rabbit.class, island));
        tasks.add(new AnimalDeployer(Sheep.class, island));

        ExecutorService service = Executors.newWorkStealingPool();


        tasks.stream()
                .forEach(e ->service.submit(e));

        try {
            System.out.println(ANIMAL_DEPLOYING_MESSAGE);
            service.shutdown();
            if (!service.awaitTermination(ExecutorsAwaitingConstants.FAUNA_IMMIGRATE_AWAITING, TimeUnit.SECONDS)) {
              service.shutdownNow();
            }
                System.out.println(END_OF_DEPLOYING_MESSAGE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}

package com.ru.javarush.echo.nikolaymelnikov.javarushproject.util;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.interfaces.Printer;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Cell;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;



@Getter
@Setter
public class StatisticsPrinter implements Printer {

    private static final Logger loggerToConsole = Logger.getLogger("APP1");
    private static final Logger loggerToFile = Logger.getLogger("APP2");
    private Map<String, Long> quantityOfAnimals = new ConcurrentHashMap<>();
    private Map<String, Long> quantityOfPlants = new ConcurrentHashMap<>();
    private AtomicInteger carnivoresQuantity = new AtomicInteger(0);
    private AtomicInteger herbivoresQuantity = new AtomicInteger(0);
    private ExecutorService service = Executors.newSingleThreadExecutor();
    public static final String NAME_OF_THREAD = "Statistics";
    public static final String SMILES_OR_EMOJI_MESSAGE = "What kind of statistics you want to see, names or emoji? Type the answer below";
    private static final String CARNIVORES_DEAD = "All carnivores are down. Please, stop tormenting this cursed world.";
    private static final String HERBIVORES_DEAD = "All herbivores died. We recommend that you stop torturing this weak world.";
    private static String answer = "emoji";

    /**
     * If you want to use this StatisticsPrinter in another world, just change "island" parameter
     */
    private Island island = Island.getInstance();

    @Override
    public void print() {
        Thread.currentThread().setName(NAME_OF_THREAD);
        quantityOfPlants.clear();
        quantityOfAnimals.clear();
        carnivoresQuantity.set(0);
        herbivoresQuantity.set(0);

        Arrays.stream(island.getIslandField()).forEach(e -> Arrays.stream(e).forEach(cell -> service.submit(() -> {
            if ("emoji".equalsIgnoreCase(answer)) {
                getStatisticsWithEmoji(cell);
            } else {
                getStatisticsWithNames(cell);
            }
        })));

        try {
            if (service.awaitTermination(ExecutorsAwaitingConstants.STATISTIC_PRINTER_AWAITING, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
            service.shutdown();
        } catch (InterruptedException e) {
            loggerToFile.error(e + " " + "StatisticsPrinter is interrupted");
            Thread.currentThread().interrupt();
        }
        quantityOfAnimals.forEach((k,v)->loggerToConsole.log(Level.INFO, k +" : "+v ));
        System.out.println();
        quantityOfPlants.forEach((k,v)->loggerToConsole.log(Level.INFO,k +" : "+v ));
        if (carnivoresQuantity.get() == 0) {
            System.out.println(CARNIVORES_DEAD);

        } else if (herbivoresQuantity.get() == 0) {
            System.out.println(HERBIVORES_DEAD);
        }
    }

    private void decideNamesOrEmoji() {
        if (answer.isEmpty()) {
            while (!answer.equalsIgnoreCase("emoji") || !answer.equalsIgnoreCase("names")) {
                loggerToConsole.log(Level.INFO ,SMILES_OR_EMOJI_MESSAGE);
                choosingTypeOfStatisticsByUser();
            }
        }
    }

    private synchronized void choosingTypeOfStatisticsByUser() {
        while (!answer.equals("emoji") || !answer.equals("names")) {
            answer = new Scanner(System.in).nextLine();
        }
    }

    private void getStatisticsWithEmoji(Cell cell) {
        cell.getFauna().forEach(e -> quantityOfAnimals.merge(e.getEmoji(), 1L, Long::sum));
        cell.getFlora().forEach(e -> quantityOfPlants.merge(e.getEmoji(), 1L, Long::sum));
        carnivoresQuantity.addAndGet(cell.getCarnivoreAnimalsQuantity());
        herbivoresQuantity.addAndGet(cell.getHerbivoreAnimalsQuantity());
    }
    private void getStatisticsWithNames(Cell cell) {
        cell.getFauna().forEach(e -> quantityOfAnimals.merge(e.getName(), 1L, Long::sum));
        cell.getFlora().forEach(e -> quantityOfPlants.merge(e.getName(), 1L, Long::sum));
        carnivoresQuantity.addAndGet(cell.getCarnivoreAnimalsQuantity());
        herbivoresQuantity.addAndGet(cell.getHerbivoreAnimalsQuantity());
    }
}

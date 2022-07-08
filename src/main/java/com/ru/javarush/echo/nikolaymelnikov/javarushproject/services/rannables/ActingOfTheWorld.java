package com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.rannables;


import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Cell;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.ExecutorsAwaitingConstants;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.util.StatisticsPrinter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/***
 * При помощи ExecutorService в данном классе производится обработка каждой клетки в отдельном потоке.
 * Ввиду очень медленного метода eat() принято решение распараллелить процесс выполнения дня
 */
public class ActingOfTheWorld implements Runnable {
    public static final String START_MESSAGE = "Animals move...";
    public static final String END_MESSAGE = "End of the day...";
    public static final int QUANTITY_OF_THREADS = 5;
    public Island island;
    public StatisticsPrinter printer = new StatisticsPrinter();

    public ExecutorService service = Executors.newFixedThreadPool(QUANTITY_OF_THREADS);

    public ActingOfTheWorld(Island island) {
        this.island = island;
    }

    public void run() {
        System.out.println(START_MESSAGE);
        for (int x = 0; x < island.getWight(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Cell cell = island.getCell(x, y);
                if (!cell.getFauna().isEmpty()) {
                    service.submit(new DayInCellProcessor(cell));
                }
            }
        }
        service.shutdown();
        try {
            if (!service.awaitTermination(ExecutorsAwaitingConstants.ACT_OF_WORLD_AWAITING, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e + " WorldActingInterrupted");
        }
        System.out.println(END_MESSAGE);
        printer.print();
    }
}

package com.ru.javarush.echo.nikolaymelnikov.javarushproject.util;

public class ExecutorsAwaitingConstants {
    private ExecutorsAwaitingConstants() {
    }

    /**
     * These constants deciding how much time Executors will work
     * For now it is minimum time to full and correct work for this
     *If you want to increase quantity of types of animals, be careful with this parameter an increase this if necessary
     */
    public static final int FAUNA_IMMIGRATE_AWAITING = 15;
    public static final int GRASS_SEEDER_AWAITING = 1;
    public static final int STATISTIC_PRINTER_AWAITING = 3;
    public static final int ACT_OF_WORLD_AWAITING = 10;
}

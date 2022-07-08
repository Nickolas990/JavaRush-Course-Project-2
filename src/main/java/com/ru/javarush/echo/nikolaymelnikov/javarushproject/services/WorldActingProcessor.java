package com.ru.javarush.echo.nikolaymelnikov.javarushproject.services;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.rannables.ActingOfTheWorld;

public class WorldActingProcessor {

    public void process() {
        Thread thread = new Thread(new ActingOfTheWorld(Island.getInstance()));

        thread.start();
    }
}

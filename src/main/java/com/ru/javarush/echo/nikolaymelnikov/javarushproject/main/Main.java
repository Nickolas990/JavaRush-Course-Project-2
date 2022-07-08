package com.ru.javarush.echo.nikolaymelnikov.javarushproject.main;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.services.Runner;

public class Main {

    public static final String END_MESSAGE = "Ended";

    public static void main(String[] args) {

        new Runner(Island.getInstance()).startSimulation();
        System.out.println(END_MESSAGE);




    }
}

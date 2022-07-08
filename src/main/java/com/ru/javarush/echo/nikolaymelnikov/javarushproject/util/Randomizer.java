package com.ru.javarush.echo.nikolaymelnikov.javarushproject.util;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {


    public static int randomize(int leftBound, int rightBound) {
        return ThreadLocalRandom.current().nextInt(leftBound, rightBound);
    }
}

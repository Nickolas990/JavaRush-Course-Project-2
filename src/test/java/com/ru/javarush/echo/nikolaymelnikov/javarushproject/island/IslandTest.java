package com.ru.javarush.echo.nikolaymelnikov.javarushproject.island;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class IslandTest {
    @Test
    void isSingletonTest() {
        Island island = Island.getInstance();
        Island island1 = Island.getInstance();
        assertSame(island, island1);
    }
}

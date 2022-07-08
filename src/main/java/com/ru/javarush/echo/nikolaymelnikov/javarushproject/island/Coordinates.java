package com.ru.javarush.echo.nikolaymelnikov.javarushproject.island;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Coordinates {
    int x = 0;
    int y = 0;

    public Coordinates() {}

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

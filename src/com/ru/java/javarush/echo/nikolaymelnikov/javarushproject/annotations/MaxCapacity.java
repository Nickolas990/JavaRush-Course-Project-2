package com.ru.java.javarush.echo.nikolaymelnikov.javarushproject.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MaxCapacity {
    int value();
}

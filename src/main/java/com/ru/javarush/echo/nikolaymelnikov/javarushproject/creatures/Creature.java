package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.interfaces.Mortal;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Cell;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.settings.Settings;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
public abstract class Creature implements Mortal {
    private Coordinates position;
    protected Island island;
    protected Settings settings;
    protected boolean isAlive = true;
    protected String name;
    protected double weight;
    protected int maxEnergy;
    protected AtomicInteger currentEnergy = new AtomicInteger(0);
    protected double maxHunger;
    protected double currentHanger;
    protected int starve;
    protected int maxCapacity;
    protected Map<String, Double> luck;
    protected Cell cell;
    protected String emoji;


    protected static int maxCapacityInCell;

    public Creature(Coordinates position, Island island) {
        this.position = position;
        this.island = island;
        settings = island.getSettings();
        cell = island.getCell(position);
        setName(this.getClass().getSimpleName());

    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates coordinates) {
        position.setX(coordinates.getX());
        position.setY(coordinates.getY());
    }


    @Override
    public void die() {
        isAlive = false;
        leaveCell();
    }

    public abstract void leaveCell();

    @Override
    public String toString() {
        return "{" + name +
                '}';
    }
    public int reduceEnergy() {
        return currentEnergy.decrementAndGet();
    }



    public void restoreEnergy() {
        if (currentEnergy.get() <= 0) {
            currentEnergy.set(maxEnergy);
            currentHanger -= maxEnergy;
            if (currentHanger < 0) {
                currentHanger = 0;
                starve--;
                if (starve < 0) {
                    die();
                }
            }
        }
    }
}

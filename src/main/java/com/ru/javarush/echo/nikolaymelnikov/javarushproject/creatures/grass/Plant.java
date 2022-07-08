package com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.grass;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.annotations.MaxCapacity;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.Creature;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.interfaces.Mortal;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Cell;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Coordinates;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.island.Island;
import lombok.Getter;

@MaxCapacity(200)
@Getter
public class Plant extends Creature implements Mortal {
    int maxCapacityInCell;

    public Plant(Coordinates position, Island island) {
        super(position, island);
    }

    {
        name = "Plant";
        weight = settings.getPlantsWeight();
        maxCapacityInCell = settings.getPlantsCapacityPerCell();
        emoji = settings.getPlantEmoji();
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public void leaveCell() {
        Cell cell = island.getCell(getPosition());
        cell.getFlora().remove(this);
        cell.getQuantityOfGrass().merge(getEmoji(), 1L, (oldVal, newVal) -> oldVal - newVal);
        if (cell.getQuantityOfGrass().get(getName()) < 0) {
            cell.getQuantityOfGrass().remove(getName());
        }
        cell.removeThis(this);

    }
}

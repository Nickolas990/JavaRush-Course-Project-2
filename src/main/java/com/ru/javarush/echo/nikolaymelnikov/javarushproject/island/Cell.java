package com.ru.javarush.echo.nikolaymelnikov.javarushproject.island;

import com.ru.javarush.echo.nikolaymelnikov.javarushproject.annotations.MaxCapacity;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.Creature;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.Animal;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.carnivoreanimals.CarnivoreAnimal;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.animals.herbivoreanimals.HerbivoreAnimal;
import com.ru.javarush.echo.nikolaymelnikov.javarushproject.creatures.grass.Plant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@ToString
@Getter
@Setter
public class Cell {

    @ToString.Exclude
    private Coordinates coordinates = new Coordinates();
    private Map<String, Long> creaturesInCell = new ConcurrentHashMap<>();
    private Map<String, Integer> currentCapacityOfCell = new ConcurrentHashMap<>();
    private Map<String, Long> quantityOfGrass = new ConcurrentHashMap<>();

    private List<Animal> fauna = new CopyOnWriteArrayList<>();
    private  List<Plant> flora = new CopyOnWriteArrayList<>();


    public Cell(int x, int y) {
        coordinates.setX(x);
        coordinates.setY(y);
    }

    public Cell(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void addAnimalInCell(Animal animal) {
        fauna.add(animal);
        creaturesInCell.merge(animal.getName(), 1L, Long::sum);
        updateCapacity(animal);
        if (currentCapacityOfCell.get(animal.getName()) <=0 ){
            animal.die();
        }
    }
    private void updateCapacity(Creature creature) {
        currentCapacityOfCell.putIfAbsent(creature.getName(), creature.getClass().getAnnotation(MaxCapacity.class).value());
        currentCapacityOfCell.merge(creature.getName(), 1, (oldVal, newVal) -> oldVal - newVal);
    }

    public void addPlantInCell(Plant plant) {
        flora.add(plant);
        quantityOfGrass.merge(plant.getName(), 1L, Long::sum);
    }



    public void removeThis(Creature creature) {
        creaturesInCell.merge(creature.getName(), 1L, (oldVal, newVal) -> oldVal - newVal);
        if (creaturesInCell.get(creature.getName()) < 0 || creaturesInCell.containsKey(creature.getName())) {
            creaturesInCell.remove(creature.getName());
        }
    }
    public Integer getHerbivoreAnimalsQuantity() {
        return fauna.stream()
                .filter(HerbivoreAnimal.class::isInstance)
                .toList()
                .size();
    }
    public Integer getCarnivoreAnimalsQuantity() {
        return fauna.stream()
                .filter(CarnivoreAnimal.class::isInstance)
                .toList()
                .size();
    }

    public Integer getPlantsQuantity() {
        return flora.size();
    }
    private void killAll() {
        fauna.stream().forEach(Creature::die);
        flora.stream().forEach(Creature::die);
    }
}

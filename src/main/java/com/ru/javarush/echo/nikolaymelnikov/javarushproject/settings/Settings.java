package com.ru.javarush.echo.nikolaymelnikov.javarushproject.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

@Getter
@Setter
@ToString
public class Settings {
    public int wight;
    public int height;

    private int starve;
    private int plantsWeight;
    private int plantsCapacityPerCell;
    private int plantsNutritionalValue;
    private String plantEmoji;

    private AnimalCharacteristics[] animalCharacteristics;



    public static Settings initialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        Settings settings;
        try {
            settings = objectMapper.readValue(new BufferedReader(new InputStreamReader(getFileFromResource("settings.json"))), Settings.class);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e + " There was a problem with the settings.json file. Check that it is in the settings root directory and matches the Settings() class");
        }
        return settings;
    }

    private static InputStream getFileFromResource(String filename) throws URISyntaxException {
        InputStream resource = Settings.class.getClassLoader().getResourceAsStream(filename);
        if (resource == null) {
            throw new IllegalArgumentException("File settings.json not found");
        } else {
            return resource;
        }
    }
}
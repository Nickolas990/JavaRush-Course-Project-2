package com.ru.javarush.echo.nikolaymelnikov.javarushproject.settings;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public class LuckTable {
    private static Map<String, Map<String, Double>> luckTable;

    static {
        luckTable = new Gson().fromJson(new BufferedReader(new InputStreamReader(getFileFromResource("luck.json"))), HashMap.class);
    }

    public static Map<String, Map<String, Double>> getLuckTable() {
        return luckTable;
    }

    private static InputStream getFileFromResource(String filename) {
        InputStream resource = Settings.class.getClassLoader().getResourceAsStream(filename);
        if (resource == null) {
            throw new IllegalArgumentException("File settings.json not found");
        } else {
            return resource;
        }
    }
}



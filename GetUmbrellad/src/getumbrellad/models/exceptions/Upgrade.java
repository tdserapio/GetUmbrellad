/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getumbrellad.models.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Upgrade {
    
    private String type;
    private int value;
    
    public static ArrayList<String> UPGRADE_NAMES;
    public static ArrayList<Integer> UPGRADE_VALUES;
    public static ArrayList<String> UPGRADE_DESCRIPTIONS;
    
    public Upgrade(String type, int value) {
        this.type = type;
        this.value = value;
        readJsonFile();
    }
    
    public Upgrade() {
        System.out.println("HAHAHA");
        UPGRADE_NAMES = new ArrayList<>();
        UPGRADE_VALUES = new ArrayList<>();
        UPGRADE_DESCRIPTIONS = new ArrayList<>();
        readJsonFile();
    }
    
    public String getType() {
        return this.type;
    }
    public int getValue() {
        return this.value;
    }
    
    public void upgradeUmbrella() {
        System.out.println("Umbrella is upgraded!");
    }
    
    public void remove() {
        this.type = "";
        this.value = 0;
    }
    
    public void readJsonFile() {
        // Path is relative to src/main/resources or classpath root
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("getumbrellad/resources/misc_files/upgrades.csv");

        if (inputStream == null) {
            System.out.println("File not found!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip header if necessary
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                String upgradeName = parts[0].trim();
                int upgradeValue = Integer.parseInt(parts[1].trim());
                String upgradeDescription = parts[2].trim();
                UPGRADE_NAMES.add(upgradeName);
                UPGRADE_VALUES.add(upgradeValue);
                UPGRADE_DESCRIPTIONS.add(upgradeDescription);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}

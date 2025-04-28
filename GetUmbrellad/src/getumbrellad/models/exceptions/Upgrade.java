/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getumbrellad.models.exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class Upgrade {
    
    private String type;
    private int value;
    private String description;
    private boolean isOwned;
    private int cost;
    
    public static ArrayList<Upgrade> upgrades;
    
    public Upgrade(String type, int value, String description, boolean isOwned, int cost) {
        this.type = type;
        this.value = value;
        this.description = description;
        this.isOwned = isOwned;
        this.cost = cost;
    }
    
    public Upgrade() {
        readJsonFile();
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public boolean getIsOwned() {
        return this.isOwned;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public void setIsOwned(boolean newValue) {
        isOwned = newValue;
    }
    
    public void upgradeUmbrella() {
        System.out.println("Umbrella is upgraded!");
    }
    
    public void remove() {
        this.type = "";
        this.value = 0;
    }
    
    public void readJsonFile() {
        
        upgrades = new ArrayList<Upgrade>();
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
                boolean upgradeOwned = (Integer.parseInt(parts[3].trim()) > 0);
                int upgradeCost = Integer.parseInt(parts[4].trim());
                                
                Upgrade currUPG = new Upgrade(upgradeName, upgradeValue, upgradeDescription, upgradeOwned, upgradeCost);
                upgrades.add(currUPG);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void writeJsonFile() throws PlayerNotFoundException {

        // Path is relative to src/main/resources or classpath root
        URL dirUrl = getClass().getClassLoader().getResource("getumbrellad/resources/misc_files/");
        if (dirUrl == null) {
            throw new PlayerNotFoundException("Upgrade directory not found!");
        }

        try {
            Path outPath = Paths.get(dirUrl.toURI()).resolve("upgrades.csv");

            try (BufferedWriter writer = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                // Write header
                writer.write("upgrade_name, upgrade_value, description, owned, cost");
                writer.newLine();
                
                
                for (Upgrade currUPG: upgrades) {
                    // Write upgrade data
                    int isAlreadyOwned = (currUPG.getIsOwned()) ? 1 : 0;
                    writer.write(currUPG.getType() + "," +
                                 currUPG.getValue() + "," +
                                 currUPG.getDescription() + "," +
                                 isAlreadyOwned + "," +
                                 currUPG.getCost());
                    writer.newLine();
                }
                
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}

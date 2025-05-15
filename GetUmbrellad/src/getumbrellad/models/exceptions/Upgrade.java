package getumbrellad.models.exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class Upgrade {
    
    private String shopOwner, name;
    private int value;
    
    private String increasedStat;
    private String description;
    private boolean isOwned;
    
    private int cost;
    
    public static ArrayList<Upgrade> upgrades = new ArrayList<>();
    
    public Upgrade(String shopOwner, String name, int value, String increasedStat, String description, boolean isOwned, int cost) {
        
        this.shopOwner = shopOwner;
        this.name = name;
        this.value = value;
        this.increasedStat = increasedStat;
        this.description = description;
        this.isOwned = isOwned;
        this.cost = cost;

        upgrades.add(this);
        
    }

    public String getShopOwner() {
        return this.shopOwner;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public String getIncreasedStat() {
        return this.increasedStat;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getIsOwned() {
        return this.isOwned;
    }

    public int getCost() {
        return this.cost;
    }

    public void setIsOwned(boolean newValue) {
        isOwned = newValue;
        updateUpgrades();
    }

    public static void readUpgradesCSV() {
        
        upgrades = new ArrayList<>();
        InputStream inputStream = Upgrade.class.getResourceAsStream("/getumbrellad/resources/misc_files/stores.csv");

        if (inputStream == null) {
            System.out.println("File not found!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            
            String line;
            boolean isFirstLine = true;
            

            while ((line = reader.readLine()) != null) {
                
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");

                String upgradeOwner = parts[0].trim();
                String upgradeName = parts[1].trim();
                int upgradeValue = Integer.parseInt(parts[2].trim());
                String increasedStat = parts[3].trim();
                String upgradeDescription = parts[4].trim();
                boolean upgradeOwned = Integer.parseInt(parts[5].trim()) > 0;
                int upgradeCost = Integer.parseInt(parts[6].trim());

                Upgrade newUPG = new Upgrade(upgradeOwner, upgradeName, upgradeValue, increasedStat, upgradeDescription, upgradeOwned, upgradeCost);
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeUpgradesCSV() throws PlayerNotFoundException, URISyntaxException {
        URL dirUrl = Upgrade.class.getResource("/getumbrellad/resources/misc_files/");
        if (dirUrl == null) {
            throw new PlayerNotFoundException("Store directory not found!");
        }

        Path outPath = Paths.get(dirUrl.toURI()).resolve("stores.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            // Write header
            writer.write("shop_owner,upgrade_name,upgrade_value,increased_stat,description,owned,cost");
            writer.newLine();

            for (Upgrade currUPG : upgrades) {
                int isAlreadyOwned = (currUPG.getIsOwned()) ? 1 : 0;
                writer.write(
                        currUPG.getShopOwner() + "," +
                        currUPG.getName() + "," +
                        currUPG.getValue() + "," +
                        currUPG.getIncreasedStat() + "," +
                        currUPG.getDescription() + "," +
                        isAlreadyOwned + "," +
                        currUPG.getCost());
                writer.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUpgrades() {
        try {
            writeUpgradesCSV();
        } catch (Exception exc) {
            System.out.println("No errors are supposed to happen. Please debug.");
            return;
        }
        readUpgradesCSV();
    }
}

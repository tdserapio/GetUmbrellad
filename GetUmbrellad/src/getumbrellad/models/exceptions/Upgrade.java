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

/**
 * Represents a shop upgrade in the game that can be purchased from NPCs.
 * Each upgrade affects a specific player stat (e.g., maxHP, defense) and 
 * can be read from or written to a CSV file.
 */
public class Upgrade {
    
    /**
     * The name of the shop owner offering this upgrade.
     */
    private String shopOwner;

    /**
     * The name of the upgrade.
     */
    private String name;

    /**
     * The numerical value or magnitude of the upgrade.
     */
    private int value;

    /**
     * The stat that this upgrade increases (e.g., "hp", "jump", "defense").
     */
    private String increasedStat;

    /**
     * The description of the upgrade.
     */
    private String description;

    /**
     * Whether the upgrade has already been purchased or owned by the player.
     */
    private boolean isOwned;

    /**
     * The cost of the upgrade in coins.
     */
    private int cost;

    /**
     * All upgrades read from or written to CSV.
     */
    public static ArrayList<Upgrade> upgrades = new ArrayList<>();

    /**
     * Constructs an Upgrade.
     * Automatically adds this upgrade to the static upgrades list.
     *
     * @param shopOwner     the name of the NPC shop owner offering this upgrade
     * @param name          the name of the upgrade
     * @param value         the value or magnitude of the upgrade
     * @param increasedStat the player stat affected by the upgrade
     * @param description   a brief description of the upgrade
     * @param isOwned       whether the upgrade has already been purchased
     * @param cost          the coin cost of the upgrade
     */
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

    /**
     * Returns the name of the shop owner offering this upgrade.
     *
     * @return the shop owner's name
     */
    public String getShopOwner() {
        return this.shopOwner;
    }

    /**
     * Returns the name of the upgrade.
     *
     * @return the upgrade's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the value or magnitude of the upgrade.
     *
     * @return upgrade value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Returns the stat that this upgrade affects.
     *
     * @return affected player stat
     */
    public String getIncreasedStat() {
        return this.increasedStat;
    }

    /**
     * Returns a short description of the upgrade.
     *
     * @return upgrade description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns whether this upgrade has been purchased by the player.
     *
     * @return true if owned, false otherwise
     */
    public boolean getIsOwned() {
        return this.isOwned;
    }

    /**
     * Returns the cost of this upgrade in coins.
     *
     * @return the upgrade's cost
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Sets whether the upgrade is owned and updates the CSV data accordingly.
     *
     * @param newValue true if the upgrade is now owned, false otherwise
     */
    public void setIsOwned(boolean newValue) {
        isOwned = newValue;
        updateUpgrades();
    }

    /**
     * Reads upgrade data from stores.csv located in the resource directory,
     * and repopulates the upgrades list.
     */
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

    /**
     * Writes the current list of upgrades in upgrades to stores.csv.
     *
     * @throws PlayerNotFoundException if the store directory cannot be found
     * @throws URISyntaxException if the resource URI for the store path is invalid
     */
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

    /**
     * Updates the CSV by rewriting and reloading all upgrades from the file.
     * If an exception occurs, a debug message is printed.
     */
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

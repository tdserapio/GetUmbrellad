package getumbrellad.models.exceptions;

import getumbrellad.views.LevelGameplayPanelGUI;
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

public class Player extends Character implements Spawnable {
    
    private int money;

    public Player(String name, LevelGameplayPanelGUI panel, int x, int y, int maxHP, int hp, int damage) {
        super(name, panel, x, y, maxHP, hp, damage);
        this.money = 0;
    }
    
    public Player(String name, int hp, int damage, int money) {
        super(name, null, -1, -1, -1, hp, damage);
        this.money = money;
    }
    
    public Player readPlayer(String fileName) throws PlayerNotFoundException {
        
        // Path is relative to src/main/resources or classpath root
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("getumbrellad/resources/misc_files/" + fileName);

        if (inputStream == null) {
            throw new PlayerNotFoundException("Player file not found!");
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
                Player p = new Player(parts[0], Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()));
                return p;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    public void writePlayer(String fileName, Player p) throws PlayerNotFoundException {

        // Path is relative to src/main/resources or classpath root
        URL dirUrl = getClass().getClassLoader().getResource("getumbrellad/resources/misc_files/");
        if (dirUrl == null) {
            throw new PlayerNotFoundException("Player directory not found!");
        }

        try {
            Path outPath = Paths.get(dirUrl.toURI()).resolve(fileName);

            try (BufferedWriter writer = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                // Write header
                writer.write("name, hp, damage, money");
                writer.newLine();

                // Write player data
                writer.write(p.getName() + "," +
                             p.getHP() + "," +
                             p.getDamage() + "," +
                             p.getMoney());
                writer.newLine();
                
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    
    public int getDamage() {
        return this.damage;
    }
    public int getMoney() {
        return this.money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public int getHP() {
        return this.hp;
    }
    
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void buff(String stat, int amount) {
        switch(stat) {
            case "coin":
                this.money += amount;
                break;
            case "health":
                this.hp += amount;
                break;
            default:
                //throwBuffTypeNotFound
        }
    }

}

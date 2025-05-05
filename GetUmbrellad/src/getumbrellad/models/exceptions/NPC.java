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

public class NPC extends Character implements Spawnable {
    private String dialogue;
    //private Shop shop;
    
    public NPC(String name, LevelGameplayPanelGUI panel, int x, int y, int maxHP, int hp, int damage, String dialogue) {
        super(name, panel, x, y, maxHP, hp, damage);
        this.dialogue = dialogue;
    }
    
    
    public void interact(){
        System.out.println(dialogue);
    }
    
    public void openShop(){
        //opens the shop
    }
    
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}

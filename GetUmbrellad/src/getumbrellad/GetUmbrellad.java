package getumbrellad;

import getumbrellad.models.exceptions.Player;
import getumbrellad.models.exceptions.Upgrade;
import getumbrellad.views.MainMenuGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetUmbrellad {
    
    public static void main(String[] args) {
        
        Upgrade.readUpgradesCSV();
        new MainMenuGUI();
        
    }
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appliedlevelgameplaytesting;
import javax.swing.JFrame;

/**
 *
 * @author bruv
 */
public class LevelGameplayGUI extends JFrame{
    
    private LevelGameplayPanelGUI panel;
    private LevelGameplayGUIController controller; 
    
    public LevelGameplayGUI() {
        super("Level Gameplay");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 600);
        
        panel = new LevelGameplayPanelGUI();
        panel.setLocation(0,0);
        panel.setSize(this.getSize());
        panel.setVisible(true);
        panel.setFocusable(true);
        this.add(panel);
        
        //don't change this
        addMouseMotionListener(new LevelGameplayPanelGUIController(panel));
        addKeyListener(new LevelGameplayPanelGUIController(panel));
        this.setFocusable(true);
        this.setResizable(false);
        this.setVisible(true);
    }
}

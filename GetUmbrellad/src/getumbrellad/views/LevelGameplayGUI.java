package getumbrellad.views;

import getumbrellad.controllers.LevelGameplayGUIController;
import getumbrellad.models.exceptions.Spawnable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class LevelGameplayGUI extends JPanel {

    private final JFrame frame;
    private LevelGameplayGUIController controller;

    public LevelGameplayGUI() {

        frame = new JFrame("Level Gameplay");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setResizable(false);

        this.setLayout(null);
        this.setBounds(0, 0, 900, 600);

        frame.add(this);
        frame.setVisible(true);

        setFocusable(true);
        
        this.controller = new LevelGameplayGUIController(this);
        this.addKeyListener(controller);
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);
        
        while (!this.controller.getHasLoadedPlayer()) continue;

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream is = getClass().getResourceAsStream("../resources/Lexend.ttf");
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, is));
        } catch (Exception anyException) {
            System.out.println(anyException.getMessage());
            return;
        }

        JButton menuButton = new JButton();
        try {
            menuButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../resources/menubutton.png"))));
        } catch (IOException ioe) {
            menuButton.setText("Menu Button");
        }
        menuButton.setBounds(30, 30, 60, 60);
        this.add(menuButton);
        menuButton.addActionListener(controller);

        JLabel coinIcon = new JLabel(new ImageIcon(getClass().getResource("../resources/umbrellacoin.png")));
        coinIcon.setBounds(760, 30, 24, 24);
        this.add(coinIcon);
        
        JLabel hpIcon = new JLabel(new ImageIcon(getClass().getResource("../resources/healthpoints.png")));
        hpIcon.setBounds(760, 56, 24, 24);
        this.add(hpIcon);

        JLabel coinCount = new JLabel(this.controller.getPlayer().getMoney() + "");
        coinCount.setFont(new Font("Lexend", Font.PLAIN, 24));
        coinCount.setForeground(Color.BLACK);
        coinCount.setBounds(800, 30, 80, 24);
        this.add(coinCount);

        JLabel currentHP = new JLabel(this.controller.getPlayer().getHP() + "");
        currentHP.setFont(new Font("Lexend", Font.PLAIN, 24));
        currentHP.setForeground(Color.BLACK);
        currentHP.setBounds(800, 56, 80, 24);
        this.add(currentHP);
        
        this.controller.setupFrontend(menuButton, coinCount, currentHP);
        
    }

    public JFrame getFrame() {
        return frame;
    }

    public LevelGameplayGUIController getController() {
        return controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ArrayList<Spawnable> entities = new ArrayList<>(controller.getSpawnables());
        for (Spawnable spawn : entities) {
            spawn.draw(g2);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package getumbrellad.controllers;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import getumbrellad.models.exceptions.Upgrade;
import getumbrellad.views.MainMenuGUI;


public class InventoryGUIController implements ActionListener, MouseListener, ListSelectionListener {
    
    private JFrame InventoryGUI;
    private JLabel coinText, healthText, nameText, effectsText, descriptionText;
    private JButton exitButton;
    private JList upgradeList;
    
    
    public InventoryGUIController(JFrame InventoryGUI, JLabel coinText, JLabel healthText, JLabel nameText, JLabel effectsText, JLabel descriptionText, JButton exitButton, JList upgradeList) {
        this.InventoryGUI = InventoryGUI;
        this.coinText = coinText;
        this.healthText = healthText;
        this.nameText = nameText;
        this.effectsText = effectsText;
        this.descriptionText = descriptionText;
        this.exitButton = exitButton;
        this.upgradeList = upgradeList;
    }
    
    public void getOut(ActionEvent e){
        if (e.getSource() == exitButton) {
            MainMenuGUI mm = new MainMenuGUI();
            mm.setVisible(true);
            InventoryGUI.dispose();
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e){
        
        if (e.getValueIsAdjusting()) return;
        
        ListSelectionModel lsm = ((JList)e.getSource()).getSelectionModel();
        
        int selectedIndex = lsm.getMinSelectionIndex();
        
        nameText.setText("Name: " + Upgrade.UPGRADE_NAMES.get(selectedIndex));
        effectsText.setText("Value: " + Upgrade.UPGRADE_VALUES.get(selectedIndex));
        descriptionText.setText("<html>Description: <br>" + Upgrade.UPGRADE_DESCRIPTIONS.get(selectedIndex) + "</html>"); 
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        getOut(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

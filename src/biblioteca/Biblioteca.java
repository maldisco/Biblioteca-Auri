package biblioteca;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Biblioteca {
    
    public static void main(String[] args) {
        ImageIcon moon = new ImageIcon("resources/moon.png"); 
        Image newMoon = moon.getImage(); 
        Image resizedMoon = newMoon.getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
        moon = new ImageIcon(resizedMoon);
        
        JLabel label = new JLabel();
        label.setText("Biblioteca Auri");
        label.setIcon(moon);
        label.setHorizontalTextPosition(JLabel.CENTER); // LEFT, CENTER, RIGHT
        label.setVerticalTextPosition(JLabel.BOTTOM); // TOP, CENTER, BOTTOM
        label.setForeground(Color.BLACK);
        label.setIconTextGap(25); // espaço entre texto e icone
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setVerticalAlignment(JLabel.CENTER); // posição vertical
        label.setHorizontalAlignment(JLabel.CENTER); // posição horizontal
        
        JFrame auri = new JFrame();   // criação da frame
        auri.setTitle("Auri");  // nome da aplicação
        auri.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // fechar ao clicar no X
        auri.setResizable(false);   // tornar não redimensionável
        auri.setSize(1800,1000);  // tamanho da frame
        auri.setVisible(true);  // fazer a frame visível
        auri.add(label);
        ImageIcon lua = new ImageIcon("resources/auri-logo.png");  // criar um imageicon
        auri.setIconImage(lua.getImage());       // muda4r icone da frame
        auri.getContentPane().setBackground(new Color(0x123456));   // cor do background  
        
    } 
}

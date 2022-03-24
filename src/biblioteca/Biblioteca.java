package biblioteca;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Biblioteca {
    
    public static void main(String[] args) {
        JTextField login = new JTextField();   // login textfield
        login.setPreferredSize(new Dimension(250, 60)); // tamanho
        login.setBackground(new Color(0x123456));   // cor do fundo
        login.setForeground(Color.BLACK);   // cor da fonte
        login.setFont(new Font("Arial", Font.PLAIN, 20));   // fonte
        login.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3, true),
            "CPF",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Verdana", 1, 15),
            Color.BLACK
        ),
          javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)
        ));  // cor e formato da borda
        
        JTextField senha = new JTextField();   // senha textfield
        senha.setPreferredSize(new Dimension(250, 60)); // tamanho
        senha.setBackground(new Color(0x123456));   // cor do fundo
        senha.setForeground(Color.BLACK);   // cor da fonte
        senha.setFont(new Font("Arial", Font.PLAIN, 20));   // fonte
        senha.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3, true),
            "Senha",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Verdana", 1, 15),
            Color.BLACK
        ),
          javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3)
        ));  // cor e formato da borda
        
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
        
        JButton loginButton =  new JButton();
        loginButton.setText("Log in");
        loginButton.setBackground(Color.BLACK);   // cor do fundo
        loginButton.setForeground(new Color(0x123456));   // cor da fonte
        loginButton.setFont(new Font("Verdana", Font.BOLD, 30));
        
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(0x123456));
        loginPanel.setBounds(750, 250, 300, 500);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.add(label);
        loginPanel.add(login);
        loginPanel.add(senha);
        loginPanel.add(loginButton);
        
        JFrame auri = new JFrame();   // criação da frame
        auri.setTitle("Auri");  // nome da aplicação
        auri.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // fechar ao clicar no X
        auri.setLayout(null);
        auri.setResizable(false);   // tornar não redimensionável
        auri.setSize(1800,1000);  // tamanho da frame
        auri.setVisible(true);  // fazer a frame visível
        auri.add(loginPanel);
        auri.getContentPane().setBackground(new Color(0x123456));   // cor do background  
        
    } 
}

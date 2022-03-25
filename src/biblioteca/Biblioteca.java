package biblioteca;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Biblioteca {
    
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.buildLoginScreen();
    } 
}

class MainFrame implements ActionListener{
    JButton loginButton;
    JFrame auri;
    
    MainFrame(){
        this.auri = new JFrame();   // criação da frame
    }
    
    public void buildLoginScreen(){
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(0x123456));
        loginPanel.setBounds(750, 250, 300, 500);
        loginPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
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
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(label, c);
        
        JTextField login = new JTextField();   // login textfield
        login.setPreferredSize(new Dimension(500, 50));
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
        c.gridx = 0;
        c.gridy = 1;
        loginPanel.add(login, c);
        
        JTextField senha = new JTextField();   // senha textfield
        senha.setPreferredSize(new Dimension(500, 50));
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
        c.gridx = 0;
        c.gridy = 2;
        loginPanel.add(senha, c);
        
        this.loginButton =  new JButton();
        this.loginButton.setFocusable(false);
        this.loginButton.setText("Log in");
        this.loginButton.setBackground(Color.BLACK);   // cor do fundo
        this.loginButton.setForeground(new Color(0x123456));   // cor da fonte
        this.loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        this.loginButton.setFont(new Font("Verdana", Font.BOLD, 30));
        this.loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.loginButton.addActionListener(this);
        
        c.ipady = 10;
        c.weighty = 1.5;
        c.gridx = 0;
        c.gridy = 3;
        loginPanel.add(this.loginButton, c);
        
        this.auri.setTitle("Auri");  // nome da aplicação
        this.auri.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // fechar ao clicar no X
        this.auri.setLayout(null);
        this.auri.setResizable(false);   // tornar não redimensionável
        this.auri.setSize(1800,1000);  // tamanho da frame
        this.auri.add(loginPanel);
        this.auri.setVisible(true);  // fazer a frame visível
        this.auri.getContentPane().setBackground(new Color(0x123456));   // cor do background 
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==this.loginButton){
        }
        
    }
}
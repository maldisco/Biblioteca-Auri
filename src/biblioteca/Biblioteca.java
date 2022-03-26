package biblioteca;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Biblioteca {
    
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
    } 
}

class MainFrame implements ActionListener{
    JButton loginButton, addLivroButton, emprestimoButton, rmvLivroButton, devolButton, cdstClienteButton, cdstFuncButton;
    JFrame auri;
    JPanel loginPanel, menuPanel;
    JTextField login;
    JPasswordField senha;
    
    MainFrame(){
        this.auri = new JFrame();   // criação da frame
        auri.setTitle("Auri");  // nome da aplicação
        auri.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // fechar ao clicar no X
        auri.setLayout(null);
        auri.setResizable(false);   // tornar não redimensionável
        auri.setSize(1800,1000);  // tamanho da frame
        this.buildLoginScreen();
        auri.setVisible(true);  // fazer a frame visível
        auri.getContentPane().setBackground(new Color(0x123456));   // cor do background 
    }
    
    public void buildLoginScreen(){
        this.loginPanel = new JPanel();
        loginPanel.setBackground(new Color(0x123456));
        loginPanel.setBounds(0, 0, 1800, 1000);
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
        
        this.login = new JTextField();
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
        
        this.senha =  new JPasswordField();
        senha.setEchoChar('*');
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
        
        this.loginButton = new JButton();
        loginButton.setFocusable(false);
        loginButton.setText("Log in");
        loginButton.setBackground(Color.BLACK);   // cor do fundo
        loginButton.setForeground(new Color(0x123456));   // cor da fonte
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        loginButton.setFont(new Font("Verdana", Font.BOLD, 30));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(this);
        c.insets = new Insets(20, 5, 0, 0);
        c.gridx = 0;
        c.gridy = 4;
        loginPanel.add(this.loginButton, c);
        
        auri.add(loginPanel);
    }
    
    public void buildMenuScreen(boolean ehGerente){
        this.menuPanel = new JPanel();
        menuPanel.setBackground(new Color(0x123456));
        menuPanel.setBounds(0, 0, 1800, 1000);
        menuPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(50, 50, 50, 50);
        
        this.addLivroButton = new JButton();
        addLivroButton.setText("Adicionar Livro");
        addLivroButton.setFont(new Font("Verdana", Font.BOLD, 30));
        c.weighty = 1.5;
        c.gridx = 0;
        c.gridy = 0;
        menuPanel.add(addLivroButton, c);
        
        this.rmvLivroButton = new JButton();
        rmvLivroButton.setText("Remover Livro");
        rmvLivroButton.setFont(new Font("Verdana", Font.BOLD, 30));
        c.weighty = 1.5;
        c.gridx = 1;
        c.gridy = 0;
        menuPanel.add(rmvLivroButton, c);
        
        this.cdstFuncButton = new JButton();
        cdstFuncButton.setEnabled(ehGerente);
        cdstFuncButton.setText("Cadastro Funcionário");
        cdstFuncButton.setFont(new Font("Verdana", Font.BOLD, 30));

        c.weighty = 1.5;
        c.gridx = 2;
        c.gridy = 0;
        menuPanel.add(cdstFuncButton, c);
        
        this.emprestimoButton = new JButton();
        emprestimoButton.setText("Empréstimo");
        emprestimoButton.setFont(new Font("Verdana", Font.BOLD, 30));
        c.weighty = 1.5;
        c.gridx = 0;
        c.gridy = 1;
        menuPanel.add(emprestimoButton, c);
        
        this.devolButton = new JButton();
        devolButton.setText("Devolução");
        devolButton.setFont(new Font("Verdana", Font.BOLD, 30));
        c.weighty = 1.5;
        c.gridx = 1;
        c.gridy = 1;
        menuPanel.add(devolButton, c);
        
        this.cdstClienteButton = new JButton();
        cdstClienteButton.setText("Cadastro Cliente");
        cdstClienteButton.setFont(new Font("Verdana", Font.BOLD, 30));
        c.weighty = 1.5;
        c.gridx = 2;
        c.gridy = 1;
        menuPanel.add(cdstClienteButton, c);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==this.loginButton){
            if(login.getText().equals("07031374170")&&senha.getText().equals("123456")){
                auri.setVisible(false);
                this.buildMenuScreen(false);
                auri.remove(this.loginPanel);
                auri.add(this.menuPanel);
                auri.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(auri, "Senha ou login incorretos.");
            }
                
        }
        
    }
}
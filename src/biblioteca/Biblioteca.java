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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        Banco bd = new Banco();
        bd.carregaDados();
        MainFrame mf = new MainFrame(bd);     
        
    } 
}

class MainFrame implements ActionListener{
    JButton loginButton, confirmaAddButton, cancelaButton, addLivroButton, emprestimoButton, rmvLivroButton, devolButton, cdstClienteButton, cdstFuncButton;
    JFrame auri;
    JPanel loginPanel, menuPanel, addPanel;
    JTextField login, inputTitulo, inputGenero, inputAutor, inputISBN, inputQtd, inputAno;
    JPasswordField senha;
    Banco bd;
    Funcionario funcionarioAtual;
    
    MainFrame(Banco bd){
        this.bd=bd;
        this.auri = new JFrame();   // criação da frame
        auri.setTitle("Auri");  // nome da aplicação
        auri.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                bd.guardaDados();
                System.exit(0);
            }
        });    // fechar ao clicar no X
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
            "Usuário",
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
        addLivroButton.addActionListener(this);
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
    
    public void buildAddScreen(){
        this.addPanel =  new JPanel();
        addPanel.setBackground(new Color(0x123456));
        addPanel.setBounds(0, 0, 1800, 1000);
        addPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(30,20,30,20);
        
        JLabel label = new JLabel();
        label.setText("Título");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 1;
        addPanel.add(label, c);
        
        inputTitulo = new JTextField();
        inputTitulo.setFont(new Font("Arial", Font.PLAIN, 20));
        inputTitulo.setPreferredSize(new Dimension(500, 50));
        c.gridx = 1;
        c.gridy = 1;
        addPanel.add(inputTitulo, c);
        
        label = new JLabel();
        label.setText("Autor");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 2;
        addPanel.add(label, c);
        
        inputAutor = new JTextField();
        inputAutor.setFont(new Font("Arial", Font.PLAIN, 20));
        inputAutor.setPreferredSize(new Dimension(300, 50));
        c.gridx = 1;
        c.gridy = 2;
        addPanel.add(inputAutor, c);
        
        label = new JLabel();
        label.setText("Gênero");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 3;
        addPanel.add(label, c);
        
        inputGenero = new JTextField();
        inputGenero.setFont(new Font("Arial", Font.PLAIN, 20));
        inputGenero.setPreferredSize(new Dimension(300, 50));
        c.gridx = 1;
        c.gridy = 3;
        addPanel.add(inputGenero, c);
        
        label = new JLabel();
        label.setText("Ano de publicação");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 4;
        addPanel.add(label, c);
        
        inputAno = new JTextField();
        inputAno.setFont(new Font("Arial", Font.PLAIN, 20));
        inputAno.setPreferredSize(new Dimension(300, 50));
        c.gridx = 1;
        c.gridy = 4;
        addPanel.add(inputAno, c);
        
        label = new JLabel();
        label.setText("Quantidade de páginas");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 5;
        addPanel.add(label, c);
        
        inputQtd = new JTextField();
        inputQtd.setFont(new Font("Arial", Font.PLAIN, 20));
        inputQtd.setPreferredSize(new Dimension(300, 50));
        c.gridx = 1;
        c.gridy = 5;
        addPanel.add(inputQtd, c);
        
        label = new JLabel();
        label.setText("ISBN");
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        label.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 6;
        addPanel.add(label, c);
        
        inputISBN = new JTextField();
        inputISBN.setFont(new Font("Arial", Font.PLAIN, 20));
        inputISBN.setPreferredSize(new Dimension(300, 50));
        c.gridx = 1;
        c.gridy = 6;
        addPanel.add(inputISBN, c);
        
        confirmaAddButton = new JButton();
        confirmaAddButton.setText("Confirmar");
        confirmaAddButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        confirmaAddButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 7;
        addPanel.add(confirmaAddButton, c);
        
        this.cancelaButton = new JButton();
        cancelaButton.setText("Cancelar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        c.gridx = 1;
        c.gridy = 7;
        addPanel.add(cancelaButton, c);
    }
        
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==this.loginButton){
            Funcionario f = Banco.getFuncionario(login.getText());
            if(f != null){
                if(f.validar(senha.getText())){
                    this.funcionarioAtual = f;
                    auri.setVisible(false);
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    auri.remove(this.loginPanel);
                    auri.add(this.menuPanel);
                    auri.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(auri, "Senha incorreta.");
                }
            } else {
                JOptionPane.showMessageDialog(auri, "Usuário não encontrado.");
            }
                
        } else if(e.getSource()==this.addLivroButton){
            auri.setVisible(false);
            this.buildAddScreen();
            auri.remove(this.menuPanel);
            auri.add(this.addPanel);
            auri.setVisible(true);
        } else if(e.getSource()==this.cancelaButton){
            auri.setVisible(false);
            this.buildMenuScreen(funcionarioAtual.ehGerente());
            auri.remove(this.addPanel);
            auri.add(this.menuPanel);
            auri.setVisible(true);
        } else if(e.getSource()==this.confirmaAddButton){
            String titulo = inputTitulo.getText();
            String genero = inputGenero.getText();
            String autor = inputAutor.getText();
            String ISBN = inputISBN.getText();
            int ano =  Integer.parseInt(inputAno.getText());
            int qtd =  Integer.parseInt(inputQtd.getText());
            boolean resultado = bd.adicionaLivro(titulo, genero, autor, ISBN, ano, qtd);
            if(resultado){
                JOptionPane.showMessageDialog(auri, "Livro adicionado ao acervo.");
                auri.setVisible(false);
                this.buildMenuScreen(funcionarioAtual.ehGerente());
                auri.remove(this.addPanel);
                auri.add(this.menuPanel);
                auri.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(auri, "Livro já se encontra no acervo.");
            }
        }
        
    }
}
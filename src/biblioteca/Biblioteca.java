package biblioteca;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;

/**
 * Classe principal, contém o método main
 */
public class Biblioteca {
    
    public static void main(String[] args) {
        Banco bd = new Banco();
        bd.carregaDados();
        
        SwingUtilities.invokeLater(() -> {
            MainFrame mf = new MainFrame(bd);
        });   
        
    } 
}

/**
 * Classe utilizada para implementar a interface gráfica
 */
class MainFrame implements ActionListener{
    private JButton loginButton, confirmaAddButton, confirmaCdstButton, confirmaEmprstButton, confirmaCdstButton2, cancelaButton, addLivroButton, emprestimoButton, rmvLivroButton,
            cdstClienteButton, cdstFuncButton;
    private final JFrame auri;
    private JLabel logo;
    private JTable emprestimosAbertos;
    private JPanel loginPanel, menuPanel, addPanel, cdstClientePanel, cdstFuncPanel, emprestimoPanel;
    private JTextField login, inputTitulo, inputEditora, inputAutor, inputISBN, inputQtd, inputAno, inputNome, inputCPF, inputEndereco, inputCelular, inputData, inputCargo, inputLivro1,
            inputLivro2, inputLivro3, inputLivro4, inputLivro5, inputDuracao, inputPesquisa;
    private JPasswordField senha;
    private TableRowSorter<TableModel> pesquisa;
    private final Banco bd;
    private Funcionario funcionarioAtual;
    
    /**
     * Constrói e abre a janela de login
     * @param bd 
     */
    public MainFrame(Banco bd){
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
        buildLoginScreen();
        auri.add(loginPanel);
        auri.setVisible(true);  // fazer a frame visível
        auri.getContentPane().setBackground(new Color(0x123456));   // cor do background 
    }
    
    /**
     * Constrói a página de login
     */
    public void buildLoginScreen(){
        // Painel de login
        this.loginPanel = new JPanel();
        loginPanel.setBackground(new Color(0x123456));
        loginPanel.setBounds(0, 0, 1800, 1000);
        loginPanel.setLayout(new GridBagLayout());
        
        // Parametros do layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // Logo da biblioteca
        ImageIcon moon = new ImageIcon("resources/moon.png"); 
        Image newMoon = moon.getImage(); 
        Image resizedMoon = newMoon.getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
        moon = new ImageIcon(resizedMoon);
        this.logo = new JLabel();
        logo.setText("Biblioteca Auri");
        logo.setIcon(moon);
        logo.setHorizontalTextPosition(JLabel.CENTER); // LEFT, CENTER, RIGHT
        logo.setVerticalTextPosition(JLabel.BOTTOM); // TOP, CENTER, BOTTOM
        logo.setForeground(Color.BLACK);
        logo.setIconTextGap(25); // espaço entre texto e icone
        logo.setFont(new Font("Arial", Font.BOLD, 40));
        logo.setVerticalAlignment(JLabel.CENTER); // posição vertical
        logo.setHorizontalAlignment(JLabel.CENTER); // posição horizontal
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(logo, c);
        
        // Campo de usuário
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
        
        // Campo de senha
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
        
        // Botão para logar
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
    }
    
    /**
     * Constrói a janela de Menu, bloqueia algumas funções caso usuário não seja gerente
     * @param ehGerente 
     */
    public void buildMenuScreen(boolean ehGerente){        
        // Painel de menu
        this.menuPanel = new JPanel();
        menuPanel.setBackground(new Color(0x123456));
        menuPanel.setBounds(0, 0, 1800, 1000);
        menuPanel.setLayout(new GridBagLayout());
        
        // Parametros de layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 50, 20, 50);
        
        // Painel de botoes 
        JPanel botoes = new JPanel();
        botoes.setBackground(new Color(0x123456));
        botoes.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
        
        // Botão adicionar livro
        this.addLivroButton = new JButton();
        addLivroButton.setText("Adicionar Livro");
        addLivroButton.setFont(new Font("Verdana", Font.BOLD, 20));
        addLivroButton.addActionListener(this);
        botoes.add(addLivroButton);
        
        // Botão remover livro
        this.rmvLivroButton = new JButton();
        rmvLivroButton.setText("Remover Livro");
        rmvLivroButton.setFont(new Font("Verdana", Font.BOLD, 20));
        botoes.add(rmvLivroButton);
        
        // Botão cadastrar funcionário
        this.cdstFuncButton = new JButton();
        cdstFuncButton.setEnabled(ehGerente);
        cdstFuncButton.setText("Cadastro Funcionário");
        cdstFuncButton.setFont(new Font("Verdana", Font.BOLD, 20));
        cdstFuncButton.addActionListener(this);
        botoes.add(cdstFuncButton);
        
        // Botão Emprestar livro
        this.emprestimoButton = new JButton();
        emprestimoButton.setText("Empréstimo");
        emprestimoButton.setFont(new Font("Verdana", Font.BOLD, 20));
        emprestimoButton.addActionListener(this);
        botoes.add(emprestimoButton);
        
        // Botão cadastrar cliente
        this.cdstClienteButton = new JButton();
        cdstClienteButton.setText("Cadastro Cliente");
        cdstClienteButton.setFont(new Font("Verdana", Font.BOLD, 20));
        cdstClienteButton.addActionListener(this);
        botoes.add(cdstClienteButton);
        
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        menuPanel.add(botoes, c);
               
        // Lista de empréstimos em aberto
        JPanel emp = new JPanel();
        emp.setBackground(new Color(0x123456));
        emp.setLayout(new BoxLayout(emp, BoxLayout.PAGE_AXIS));
                
        String[] nomeColunas = {"Nome do cliente", "CPF do cliente", "Nome do funcionário","Livros emprestados"};
        DefaultTableModel model = new DefaultTableModel(nomeColunas, 0);
        for(Emprestimo ea: bd.emprestimosEmAberto()){
            model.addRow(ea.info());
        }
        this.emprestimosAbertos = new JTable(model);      
        emprestimosAbertos.getColumnModel().getColumn(3).setPreferredWidth(300);    // Tornar a coluna de livros a maior
        emprestimosAbertos.setRowHeight(25);
        emprestimosAbertos.setDefaultEditor(Object.class, null);    // Tornar as células não editáveis  
         
        this.pesquisa = new TableRowSorter<>(emprestimosAbertos.getModel());        
        this.inputPesquisa = new JTextField();
        inputPesquisa.setPreferredSize(new Dimension(1000, 30));
        inputPesquisa.getDocument().addDocumentListener(new DocumentListener(){
            
            @Override
            public void insertUpdate(DocumentEvent e){
                String text = inputPesquisa.getText();
                
                if(text.trim().length()==0){
                    pesquisa.setRowFilter(null);
                } else {
                    pesquisa.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                } 
            }
            
            @Override
            public void removeUpdate(DocumentEvent e){
                String text = inputPesquisa.getText();
                
                if(text.trim().length()==0){
                    pesquisa.setRowFilter(null);
                } else {
                    pesquisa.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            
            @Override
            public void changedUpdate(DocumentEvent e){
                throw new UnsupportedOperationException("Ainda não suportada.");
            }
        });
        emp.add(inputPesquisa);
        emp.add(Box.createRigidArea(new Dimension(0, 50)));
        emp.add(new JScrollPane(emprestimosAbertos));
        
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 5;
        menuPanel.add(emp, c);
    }
    
    /**
     * Constrói a tela de adição de livro ao acervo.
     */
    public void buildAddScreen(){
        // Painel de adição de livro
        this.addPanel =  new JPanel();
        addPanel.setBackground(new Color(0x123456));
        addPanel.setBounds(0, 0, 1800, 1000);
        addPanel.setLayout(new GridLayout(7, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Adicionar livro");
        label.setFont(new Font("Verdana", Font.BOLD, 40));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        addPanel.add(label);
        
        // Campo de título do livro (com label)
        JPanel tituloLivro = new JPanel();
        tituloLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        tituloLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Título");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        tituloLivro.add(label);
        inputTitulo = new JTextField();
        inputTitulo.setFont(new Font("Arial", Font.PLAIN, 25));
        inputTitulo.setPreferredSize(new Dimension(500, 40));
        tituloLivro.add(inputTitulo);
        addPanel.add(tituloLivro);
        
        // Campo de autor do livro (com label)
        JPanel autorLivro = new JPanel();
        autorLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        autorLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Autor");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        autorLivro.add(label);
        inputAutor = new JTextField();
        inputAutor.setFont(new Font("Arial", Font.PLAIN, 20));
        inputAutor.setPreferredSize(new Dimension(500, 40));
        autorLivro.add(inputAutor);
        addPanel.add(autorLivro);
        
        // Campo de gênero do livro (com label)
        JPanel editoraLivro = new JPanel();
        editoraLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        editoraLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Gênero");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        editoraLivro.add(label);        
        inputEditora = new JTextField();
        inputEditora.setFont(new Font("Arial", Font.PLAIN, 20));
        inputEditora.setPreferredSize(new Dimension(500, 40));
        editoraLivro.add(inputEditora);
        addPanel.add(editoraLivro);
        
        // Campo de ano de publicação do livro (com label)
        JPanel anoLivro = new JPanel();
        anoLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        anoLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Ano de publicação");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        anoLivro.add(label);
        inputAno = new JFormattedTextField(new NumberFormatter());
        inputAno.setFont(new Font("Arial", Font.PLAIN, 20));
        inputAno.setPreferredSize(new Dimension(100, 40));
        anoLivro.add(inputAno);
        addPanel.add(anoLivro);
        
        // Campo de código ISBN do livro (com label)
        JPanel ISBNLivro = new JPanel();
        ISBNLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        ISBNLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("ISBN");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        ISBNLivro.add(label);       
        inputISBN = new JTextField();
        inputISBN.setFont(new Font("Arial", Font.PLAIN, 20));
        inputISBN.setPreferredSize(new Dimension(500, 40));
        ISBNLivro.add(inputISBN);
        addPanel.add(ISBNLivro);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        botoes.setBackground(new Color(0x123456));
        confirmaAddButton = new JButton();
        confirmaAddButton.setText("Confirmar");
        confirmaAddButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        confirmaAddButton.addActionListener(this);
        confirmaAddButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(confirmaAddButton);
        
        // Botão cancelar
        this.cancelaButton = new JButton();
        cancelaButton.setText("Cancelar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        cancelaButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cancelaButton);
        addPanel.add(botoes);
    }
    
    /**
     * Constrói a tela de cadastro de cliente.
     */
    public void buildCdstClienteScreen(){
        // Painel de cadastro do cliente
        this.cdstClientePanel =  new JPanel();
        cdstClientePanel.setBackground(new Color(0x123456));
        cdstClientePanel.setBounds(0, 0, 1800, 1000);
        cdstClientePanel.setLayout(new GridLayout(7, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Cadastrar cliente");
        label.setFont(new Font("Verdana", Font.BOLD, 40));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        cdstClientePanel.add(label);
        
        // Campo de nome (com label)
        JPanel nomeCliente = new JPanel();
        nomeCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        nomeCliente.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Nome");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        nomeCliente.add(label);
        inputNome = new JTextField();
        inputNome.setFont(new Font("Arial", Font.PLAIN, 25));
        inputNome.setPreferredSize(new Dimension(500, 40));
        nomeCliente.add(inputNome);
        cdstClientePanel.add(nomeCliente);
        
        // Campo de CPF
        JPanel cpfCliente= new JPanel();
        cpfCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        cpfCliente.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("CPF");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cpfCliente.add(label);
        inputCPF = new JTextField();
        inputCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        inputCPF.setPreferredSize(new Dimension(500, 40));
        cpfCliente.add(inputCPF);
        cdstClientePanel.add(cpfCliente);
        
        // Campo de endereço (com label)
        JPanel enderecoCliente = new JPanel();
        enderecoCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        enderecoCliente.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Endereço");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        enderecoCliente.add(label);
        inputEndereco = new JTextField();
        inputEndereco.setFont(new Font("Arial", Font.PLAIN, 20));
        inputEndereco.setPreferredSize(new Dimension(500, 40));
        enderecoCliente.add(inputEndereco);
        cdstClientePanel.add(enderecoCliente);
        
        // Campo de celular (com label)
        JPanel celularCliente = new JPanel();
        celularCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        celularCliente.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Celular");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        celularCliente.add(label);        
        inputCelular = new JTextField();
        inputCelular.setFont(new Font("Arial", Font.PLAIN, 20));
        inputCelular.setPreferredSize(new Dimension(500, 40));
        celularCliente.add(inputCelular);
        cdstClientePanel.add(celularCliente);
        
        // Campo de data de nascimento (com label)
        JPanel dataCliente = new JPanel();
        dataCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        dataCliente.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Data de nascimento");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        dataCliente.add(label);
        DateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        inputData = new JFormattedTextField(data);
        inputData.setFont(new Font("Arial", Font.PLAIN, 20));
        inputData.setPreferredSize(new Dimension(250, 40));
        dataCliente.add(inputData);
        cdstClientePanel.add(dataCliente);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        botoes.setBackground(new Color(0x123456));
        confirmaCdstButton = new JButton();
        confirmaCdstButton.setText("Confirmar");
        confirmaCdstButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        confirmaCdstButton.addActionListener(this);
        confirmaCdstButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(confirmaCdstButton);
        
        // Botão cancelar
        this.cancelaButton = new JButton();
        cancelaButton.setText("Cancelar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        cancelaButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cancelaButton);
        cdstClientePanel.add(botoes);
    }
    
    /**
     * Constrói a tela de cadastro de funcionário.
     */
    public void buildCdstFuncScreen(){
        // Painel de cadastro do funcionário
        this.cdstFuncPanel =  new JPanel();
        cdstFuncPanel.setBackground(new Color(0x123456));
        cdstFuncPanel.setBounds(0, 0, 1800, 1000);
        cdstFuncPanel.setLayout(new GridLayout(10, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Cadastrar funcionário");
        label.setFont(new Font("Verdana", Font.BOLD, 35));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        cdstFuncPanel.add(label);
        
        // Campo de nome (com label)
        JPanel nomeFunc = new JPanel();
        nomeFunc.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        nomeFunc.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Nome");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        nomeFunc.add(label);
        inputNome = new JTextField();
        inputNome.setFont(new Font("Arial", Font.PLAIN, 25));
        inputNome.setPreferredSize(new Dimension(500, 40));
        nomeFunc.add(inputNome);
        cdstFuncPanel.add(nomeFunc);
        
        // Campo de CPF
        JPanel cpfFunc= new JPanel();
        cpfFunc.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        cpfFunc.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("CPF");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cpfFunc.add(label);
        inputCPF = new JTextField();
        inputCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        inputCPF.setPreferredSize(new Dimension(500, 40));
        cpfFunc.add(inputCPF);
        cdstFuncPanel.add(cpfFunc);
        
        // Campo de endereço (com label)
        JPanel enderecoFunc = new JPanel();
        enderecoFunc.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        enderecoFunc.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Endereço");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        enderecoFunc.add(label);
        inputEndereco = new JTextField();
        inputEndereco.setFont(new Font("Arial", Font.PLAIN, 20));
        inputEndereco.setPreferredSize(new Dimension(500, 40));
        enderecoFunc.add(inputEndereco);
        cdstFuncPanel.add(enderecoFunc);
        
        // Campo de celular (com label)
        JPanel celularFunc = new JPanel();
        celularFunc.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        celularFunc.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Celular");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        celularFunc.add(label);        
        inputCelular = new JTextField();
        inputCelular.setFont(new Font("Arial", Font.PLAIN, 20));
        inputCelular.setPreferredSize(new Dimension(500, 40));
        celularFunc.add(inputCelular);
        cdstFuncPanel.add(celularFunc);
        
        // Campo de data de nascimento (com label)
        JPanel dataFunc = new JPanel();
        dataFunc.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        dataFunc.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Data de nascimento");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        dataFunc.add(label);
        DateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        inputData = new JFormattedTextField(data);
        inputData.setFont(new Font("Arial", Font.PLAIN, 20));
        inputData.setPreferredSize(new Dimension(250, 40));
        dataFunc.add(inputData);
        cdstFuncPanel.add(dataFunc);
        
        // Campo de cargo (com label)
        JPanel cargoFunc = new JPanel();
        cargoFunc.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        cargoFunc.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Cargo");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cargoFunc.add(label);        
        inputCargo = new JTextField();
        inputCargo.setFont(new Font("Arial", Font.PLAIN, 20));
        inputCargo.setPreferredSize(new Dimension(500, 40));
        cargoFunc.add(inputCargo);
        cdstFuncPanel.add(cargoFunc);
        
        // Campo de Senha (com label)
        JPanel senhaFunc = new JPanel();
        senhaFunc.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        senhaFunc.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Senha");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        senhaFunc.add(label);        
        senha = new JPasswordField();
        senha.setEchoChar('*');
        senha.setFont(new Font("Arial", Font.PLAIN, 20));
        senha.setPreferredSize(new Dimension(500, 40));
        senhaFunc.add(senha);
        cdstFuncPanel.add(senhaFunc);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        botoes.setBackground(new Color(0x123456));
        confirmaCdstButton2 = new JButton();
        confirmaCdstButton2.setText("Confirmar");
        confirmaCdstButton2.setFont(new Font("Verdana", Font.PLAIN, 30));
        confirmaCdstButton2.addActionListener(this);
        confirmaCdstButton2.setPreferredSize(new Dimension(200, 50));
        botoes.add(confirmaCdstButton2);
        
        // Botão cancelar
        this.cancelaButton = new JButton();
        cancelaButton.setText("Cancelar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        cancelaButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cancelaButton);
        cdstFuncPanel.add(botoes);
    }
    
    /**
     * Constrói a tela de empréstimo.
     */
    public void buildEmprestimoScreen(){
        // Painel de empréstimo
        this.emprestimoPanel =  new JPanel();
        emprestimoPanel.setBackground(new Color(0x123456));
        emprestimoPanel.setBounds(0, 0, 1800, 1000);
        emprestimoPanel.setLayout(new GridLayout(9, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Empréstimo");
        label.setFont(new Font("Verdana", Font.BOLD, 35));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        emprestimoPanel.add(label);
        
        // Campo de CPF do cliente (com label)
        JPanel cpfCli = new JPanel();
        cpfCli.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        cpfCli.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("CPF do cliente");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cpfCli.add(label);
        inputCPF = new JTextField();
        inputCPF.setFont(new Font("Arial", Font.PLAIN, 25));
        inputCPF.setPreferredSize(new Dimension(500, 40));
        cpfCli.add(inputCPF);
        emprestimoPanel.add(cpfCli);
        
        // Campo de livro
        JPanel isbnLivro= new JPanel();
        isbnLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        isbnLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("ISBN 1");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        isbnLivro.add(label);
        inputLivro1 = new JTextField();
        inputLivro1.setFont(new Font("Arial", Font.PLAIN, 25));
        inputLivro1.setPreferredSize(new Dimension(500, 40));
        isbnLivro.add(inputLivro1);
        emprestimoPanel.add(isbnLivro);
        
        // Campo de livro
        isbnLivro= new JPanel();
        isbnLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        isbnLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("ISBN 2");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        isbnLivro.add(label);
        inputLivro2 = new JTextField();
        inputLivro2.setFont(new Font("Arial", Font.PLAIN, 25));
        inputLivro2.setPreferredSize(new Dimension(500, 40));
        isbnLivro.add(inputLivro2);
        emprestimoPanel.add(isbnLivro);
        
        // Campo de livro
        isbnLivro= new JPanel();
        isbnLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        isbnLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("ISBN 3");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        isbnLivro.add(label);
        inputLivro3 = new JTextField();
        inputLivro3.setFont(new Font("Arial", Font.PLAIN, 25));
        inputLivro3.setPreferredSize(new Dimension(500, 40));
        isbnLivro.add(inputLivro3);
        emprestimoPanel.add(isbnLivro);
        
        // Campo de livro
        isbnLivro= new JPanel();
        isbnLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        isbnLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("ISBN 4");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        isbnLivro.add(label);
        inputLivro4 = new JTextField();
        inputLivro4.setFont(new Font("Arial", Font.PLAIN, 25));
        inputLivro4.setPreferredSize(new Dimension(500, 40));
        isbnLivro.add(inputLivro4);
        emprestimoPanel.add(isbnLivro);
        
        // Campo de livro
        isbnLivro = new JPanel();
        isbnLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        isbnLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("ISBN 5");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        isbnLivro.add(label);
        inputLivro5 = new JTextField();
        inputLivro5.setFont(new Font("Arial", Font.PLAIN, 25));
        inputLivro5.setPreferredSize(new Dimension(500, 40));
        isbnLivro.add(inputLivro5);
        emprestimoPanel.add(isbnLivro);
        
        // Campo de duração em dias (com label)
        JPanel duracao = new JPanel();
        duracao.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        duracao.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Duração");
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(300, 30));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        duracao.add(label);
        inputDuracao = new JFormattedTextField(new NumberFormatter());
        inputDuracao.setFont(new Font("Arial", Font.PLAIN, 25));
        inputDuracao.setPreferredSize(new Dimension(100, 40));
        duracao.add(inputDuracao);
        emprestimoPanel.add(duracao);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        botoes.setBackground(new Color(0x123456));
        confirmaEmprstButton = new JButton();
        confirmaEmprstButton.setText("Confirmar");
        confirmaEmprstButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        confirmaEmprstButton.addActionListener(this);
        confirmaEmprstButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(confirmaEmprstButton);
                
        // Botão cancelar
        this.cancelaButton = new JButton();
        cancelaButton.setText("Cancelar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        cancelaButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cancelaButton);
        emprestimoPanel.add(botoes);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        // Ações para o botão de login
        if(e.getSource()==this.loginButton){
            Funcionario f = bd.getFuncionario(login.getText());
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
        
        // Ações para o botão de adicionar livro
        } else if(e.getSource()==this.addLivroButton){
            auri.setVisible(false);
            this.buildAddScreen();
            auri.remove(this.menuPanel);
            auri.add(this.addPanel);
            auri.setVisible(true);
        
        // Ações para o botão de cadastrar cliente
        } else if(e.getSource()==this.cdstClienteButton){
            auri.setVisible(false);
            this.buildCdstClienteScreen();
            auri.remove(this.menuPanel);
            auri.add(this.cdstClientePanel);
            auri.setVisible(true);
        
        // Ações para o botão de cadastrar funcionário
        } else if(e.getSource()==this.cdstFuncButton){
            auri.setVisible(false);
            this.buildCdstFuncScreen();
            auri.remove(this.menuPanel);
            auri.add(this.cdstFuncPanel);
            auri.setVisible(true);
        
        // Ações para o botão de empréstimo
        } else if(e.getSource()==this.emprestimoButton){    
            auri.setVisible(false);
            this.buildEmprestimoScreen();
            auri.remove(this.menuPanel);
            auri.add(this.emprestimoPanel);
            auri.setVisible(true);
            
        // Ações para o botão de cancelar adição de livro
        } else if(e.getSource()==this.cancelaButton){
            auri.setVisible(false);
            this.buildMenuScreen(funcionarioAtual.ehGerente());
            auri.getContentPane().removeAll();
            auri.add(this.menuPanel);
            auri.setVisible(true);
        
        // Ações para o botão de confirmar adição de livro
        } else if(e.getSource()==this.confirmaAddButton){
            if(inputTitulo.getText().isEmpty() || inputAutor.getText().isEmpty() || inputEditora.getText().isEmpty() ||
                    inputISBN.getText().isEmpty() || inputAno.getText().isEmpty() || inputQtd.getText().isEmpty()){
                JOptionPane.showMessageDialog(auri, "Nenhuma informação pode ficar em branco");
            } else {
                String titulo = inputTitulo.getText();
                String editora = inputEditora.getText();
                String autor = inputAutor.getText();
                String ISBN = inputISBN.getText();
                int ano =  Integer.parseInt(inputAno.getText().replaceAll("\\.",""));  
                boolean resultado = bd.adicionaLivro(titulo, autor, editora, ISBN, ano);
                if(resultado){
                    JOptionPane.showMessageDialog(auri, "Livro adicionado ao acervo.");
                    auri.setVisible(false);
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    auri.getContentPane().removeAll();
                    auri.add(this.menuPanel);
                    auri.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(auri, "Livro já se encontra no acervo.");
                }
            }
        
        // Ações para o botão de confirmar cadastrado de cliente
        } else if(e.getSource()==this.confirmaCdstButton){
            if(inputNome.getText().isEmpty() || inputCPF.getText().isEmpty() || inputEndereco.getText().isEmpty() ||
                    inputCelular.getText().isEmpty() || inputData.getText().isEmpty()){
                JOptionPane.showMessageDialog(auri, "Nenhuma informação pode ficar em branco");
            } else {
                String nome = inputNome.getText();
                String cpf = inputCPF.getText();
                String endereco = inputEndereco.getText();
                String celular = inputCelular.getText();
                String data = inputData.getText();
                boolean resultado = bd.cadastraCliente(nome, cpf, endereco, celular, data);
                if(resultado){
                    JOptionPane.showMessageDialog(auri, "Cliente cadastrado com sucesso.");
                    auri.setVisible(false);
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    auri.getContentPane().removeAll();
                    auri.add(this.menuPanel);
                    auri.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(auri, "Cliente já possui cadastro.");
                }
                
            }
            
        // Ações para o botão de confirmar cadastro de funcionario
        } else if(e.getSource()==this.confirmaCdstButton2){
            if(inputNome.getText().isEmpty() || inputCPF.getText().isEmpty() || inputEndereco.getText().isEmpty() ||
                    inputCelular.getText().isEmpty() || inputData.getText().isEmpty() || inputCargo.getText().isEmpty() || senha.getText().isEmpty()){
                JOptionPane.showMessageDialog(auri, "Nenhuma informação pode ficar em branco");
            } else {
                String nome = inputNome.getText();
                String cpf = inputCPF.getText();
                String endereco = inputEndereco.getText();
                String celular = inputCelular.getText();
                String data = inputData.getText();
                String cargo = inputCargo.getText();
                boolean resultado = bd.cadastraFuncionario(nome, cpf, endereco, celular, data, senha.getText(), cargo);
                if(resultado){
                    JOptionPane.showMessageDialog(auri, "Funcionário cadastrado com sucesso.");
                    auri.setVisible(false);
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    auri.getContentPane().removeAll();
                    auri.add(this.menuPanel);
                    auri.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(auri, "Funiconário já possui cadastro.");
                }
                
            }
        
        // Ações para botão de confirmar empréstimo
        } else if(e.getSource()==this.confirmaEmprstButton){
            if(inputCPF.getText().isEmpty()){
                JOptionPane.showMessageDialog(auri, "O CPF não pode ficar em branco.");
            } else if(inputDuracao.getText().isEmpty()){
                JOptionPane.showMessageDialog(auri, "A duração não pode ficar em branco.");
            } else if(inputLivro1.getText().isEmpty() && inputLivro2.getText().isEmpty() && inputLivro3.getText().isEmpty() && inputLivro4.getText().isEmpty() &&
                    inputLivro5.getText().isEmpty()){
                JOptionPane.showMessageDialog(auri, "Pelo menos 1 livro deve ser emprestado.");
            } else {
                List<Livro> livros = new ArrayList<>();             
                if(!inputLivro1.getText().isEmpty())
                    livros.add(bd.getLivro(inputLivro1.getText()));
                if(!inputLivro2.getText().isEmpty())
                    livros.add(bd.getLivro(inputLivro1.getText()));
                if(!inputLivro3.getText().isEmpty())
                    livros.add(bd.getLivro(inputLivro1.getText()));
                if(!inputLivro4.getText().isEmpty())
                    livros.add(bd.getLivro(inputLivro1.getText()));
                if(!inputLivro5.getText().isEmpty())
                    livros.add(bd.getLivro(inputLivro1.getText()));
                
                boolean resultado = bd.novoEmprestimo(funcionarioAtual, inputCPF.getText(), livros);
                float total = Float.parseFloat(inputDuracao.getText());
                if(resultado){
                    JOptionPane.showMessageDialog(auri, "Empréstimo realizado com sucesso.\nTotal: R$"+total);
                    auri.setVisible(false);
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    auri.getContentPane().removeAll();
                    auri.add(this.menuPanel);
                    auri.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(auri, "Cliente não cadastrado no sistema.");
                }
            }
        }
    }
}
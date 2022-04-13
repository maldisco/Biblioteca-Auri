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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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
        // Carregamento dos dados a partir dos arquivos de texto
        Banco bd = new Banco();
        bd.carregaDados();
        
        // Execução do programa
        SwingUtilities.invokeLater(() -> {
            // O programa utilizará o objeto bd para lidar com os dados
            MainFrame auri = new MainFrame(bd);
            auri.buildFrame();
        });   
        
    } 
}

/**
 * Classe utilizada para implementar a interface gráfica
 */
class MainFrame extends JFrame implements ActionListener{
    private JButton loginButton, confirmaDevButton, rmvLivroButton, confirmaAddButton, confirmaEditButton, confirmaCdstButton, confirmaEmprstButton, confirmaCdstButton2, cancelaButton, addLivroButton, emprestimoButton, acervoButton,
            cdstClienteButton, cdstFuncButton, editaLivroButton;
    private JFrame editaLivro;
    private JLabel logo;
    private JTable emprestimosAbertos, tabelaLivros;
    private JPanel loginPanel, menuPanel, addPanel, editPanel, acervoPanel, cdstClientePanel, cdstFuncPanel, emprestimoPanel;
    private JTextField login, inputTitulo, inputEditora, inputAutor, inputISBN, inputAno, inputNome, inputCPF, inputEndereco, inputCelular, inputData, inputCargo, inputLivro1,
            inputLivro2, inputLivro3, inputPesquisa;
    private JPasswordField senha;
    private TableRowSorter<TableModel> pesquisa;
    private DefaultTableModel model;
    private final Banco bd;
    private Funcionario funcionarioAtual;
    private Livro livroAtual;
    
    /**
     * Constrói e abre a janela de login
     * @param bd 
     */
    public MainFrame(Banco bd){
        this.bd=bd;
    }
    
    /**
     * Constrói a estrutura da frame
     */
    public void buildFrame(){
        this.setTitle("Auri");  // nome da aplicação
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                bd.guardaDados();
                System.exit(0);
            }
        });    // fechar ao clicar no X
        this.setLayout(null);
        this.setResizable(false);   // tornar não redimensionável
        this.setSize(1800,1000);  // tamanho da frame
        this.setLocationRelativeTo(null);
        buildLoginScreen();
        this.add(loginPanel);
        this.setVisible(true);  // fazer a frame visível
        this.getContentPane().setBackground(new Color(0x123456));   // cor do background 
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
        this.acervoButton = new JButton();
        acervoButton.setText("Ver acervo");
        acervoButton.setFont(new Font("Verdana", Font.BOLD, 20));
        acervoButton.addActionListener(this);
        botoes.add(acervoButton);
        
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
                
        String[] nomeColunas = {"Id", "Nome do cliente", "CPF do cliente", "Nome do funcionário", "Livros emprestados"};
        this.model = new DefaultTableModel(nomeColunas, 0);
        for(Emprestimo ea: bd.emprestimosEmAberto()){
            model.addRow(ea.info());
        }
        this.emprestimosAbertos = new JTable(model);
        emprestimosAbertos.getColumnModel().getColumn(0).setPreferredWidth(1);    // Tornar a coluna de Id menor
        emprestimosAbertos.getColumnModel().getColumn(4).setPreferredWidth(300);    // Tornar a coluna de livros maior
        emprestimosAbertos.setRowHeight(25);
        emprestimosAbertos.setDefaultEditor(Object.class, null);    // Tornar as células não editáveis          
        this.pesquisa = new TableRowSorter<>(emprestimosAbertos.getModel());        
        emprestimosAbertos.setRowSorter(pesquisa);

        
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
        
        confirmaDevButton = new JButton();
        confirmaDevButton.setText("Devolução");
        confirmaDevButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        confirmaDevButton.addActionListener(this);
        confirmaDevButton.setPreferredSize(new Dimension(200, 50));
        confirmaDevButton.setAlignmentX(SwingConstants.RIGHT);
        
        emp.add(inputPesquisa);
        emp.add(Box.createRigidArea(new Dimension(0, 50)));
        emp.add(new JScrollPane(emprestimosAbertos));
        emp.add(Box.createRigidArea(new Dimension(0, 20)));
        emp.add(confirmaDevButton);
        
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
        
        // Campo de editora do livro (com label)
        JPanel editoraLivro = new JPanel();
        editoraLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 50));
        editoraLivro.setBackground(new Color(0x123456));
        label = new JLabel();
        label.setText("Editora");
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
     * Constrói tabela de visualização de acervo
     * com opções de remover e alterar
     */
    public void buildAcervoScreen(){
        this.acervoPanel =  new JPanel();
        acervoPanel.setBackground(new Color(0x123456));
        acervoPanel.setBounds(0, 0, 1800, 1000);
        acervoPanel.setLayout(new GridBagLayout());
        
        // Parametros do layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titulo = new JLabel("Acervo");
        titulo.setFont(new Font("Verdana", 40, Font.BOLD));
        titulo.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 0;
        acervoPanel.add(titulo);
        
        // Lista de empréstimos em aberto
        JPanel acervo = new JPanel();
        acervo.setBackground(new Color(0x123456));
        acervo.setLayout(new BoxLayout(acervo, BoxLayout.PAGE_AXIS));
                
        String[] nomeColunas = {"Título", "Autor", "ISBN", "Editora", "Ano de publicação"};
        this.model = new DefaultTableModel(nomeColunas, 0);
        for(Livro l: bd.getAcervo()){
            model.addRow(l.info());
        }
        this.tabelaLivros = new JTable(model);   
        tabelaLivros.setRowHeight(25);
        tabelaLivros.setDefaultEditor(Object.class, null);    // Tornar as células não editáveis          
        this.pesquisa = new TableRowSorter<>(tabelaLivros.getModel());        
        tabelaLivros.setRowSorter(pesquisa);

        
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
        
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        botoes.setBackground(new Color(0x123456));
        botoes.setAlignmentX(SwingConstants.RIGHT);
        
        // Botão cancelar
        this.cancelaButton = new JButton();
        cancelaButton.setText("Voltar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        cancelaButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cancelaButton);
        
        // Botão remover        
        rmvLivroButton = new JButton();
        rmvLivroButton.setText("Remover");
        rmvLivroButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        rmvLivroButton.addActionListener(this);
        rmvLivroButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(rmvLivroButton);
        
        // Botão editar
        this.editaLivroButton = new JButton();
        editaLivroButton.setText("Editar");
        editaLivroButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        editaLivroButton.addActionListener(this);
        editaLivroButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(editaLivroButton);
        
        acervo.add(inputPesquisa);
        acervo.add(Box.createRigidArea(new Dimension(0, 50)));
        acervo.add(new JScrollPane(tabelaLivros));
        acervo.add(Box.createRigidArea(new Dimension(0, 20)));
        acervo.add(botoes);
        
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 5;        
        acervoPanel.add(acervo);
    }
    
    public void buildEditaLivroScreen(String titulo, String autor, String ISBN, String ano, String editora){
        this.editaLivro = new JFrame();
        editaLivro.setSize(new Dimension(400,600));
        editaLivro.setLocationRelativeTo(null);
        editaLivro.setTitle("Alterar livro");
        
        // Painel de adição de livro
        this.editPanel =  new JPanel();
        editPanel.setBounds(0, 0, 400, 600);
        editPanel.setLayout(new GridLayout(7, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Alterar livro");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        editPanel.add(label);
        
        // Campo de título do livro (com label)
        JPanel tituloLivro = new JPanel();
        tituloLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Título");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        tituloLivro.add(label);
        inputTitulo = new JTextField(titulo);
        inputTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        inputTitulo.setPreferredSize(new Dimension(200, 20));
        tituloLivro.add(inputTitulo);
        editPanel.add(tituloLivro);
        
        // Campo de autor do livro (com label)
        JPanel autorLivro = new JPanel();
        autorLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Autor");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        autorLivro.add(label);
        inputAutor = new JTextField(autor);
        inputAutor.setFont(new Font("Arial", Font.PLAIN, 14));
        inputAutor.setPreferredSize(new Dimension(200, 20));
        autorLivro.add(inputAutor);
        editPanel.add(autorLivro);
        
        // Campo de editora do livro (com label)
        JPanel editoraLivro = new JPanel();
        editoraLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Editora");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        editoraLivro.add(label);        
        inputEditora = new JTextField(editora);
        inputEditora.setFont(new Font("Arial", Font.PLAIN, 14));
        inputEditora.setPreferredSize(new Dimension(200, 20));
        editoraLivro.add(inputEditora);
        editPanel.add(editoraLivro);
        
        // Campo de ano de publicação do livro (com label)
        JPanel anoLivro = new JPanel();
        anoLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Ano de publicação");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(150, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        anoLivro.add(label);
        inputAno = new JFormattedTextField(new NumberFormatter());
        inputAno.setText(ano);
        inputAno.setFont(new Font("Arial", Font.PLAIN, 14));
        inputAno.setPreferredSize(new Dimension(150, 20));
        anoLivro.add(inputAno);
        editPanel.add(anoLivro);
        
        // Campo de código ISBN do livro (com label)
        JPanel ISBNLivro = new JPanel();
        ISBNLivro.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("ISBN");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        ISBNLivro.add(label);       
        inputISBN = new JTextField(ISBN);
        inputISBN.setFont(new Font("Arial", Font.PLAIN, 14));
        inputISBN.setPreferredSize(new Dimension(200, 20));
        ISBNLivro.add(inputISBN);
        editPanel.add(ISBNLivro);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        confirmaEditButton = new JButton();
        confirmaEditButton.setText("Salvar");
        confirmaEditButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        confirmaEditButton.addActionListener(this);
        confirmaEditButton.setPreferredSize(new Dimension(150, 30));
        botoes.add(confirmaEditButton);
        
        // Adiciona os componentes ao painel
        editPanel.add(botoes);
        
        // Adiciona o painel à frame
        editaLivro.add(editPanel);
        // Torna a frame visível
        editaLivro.setVisible(true);
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
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    this.remove(this.loginPanel);
                    this.add(this.menuPanel);
                    this.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "Senha incorreta.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
            }
        
        // Ações para o botão de adicionar livro
        } else if(e.getSource()==this.addLivroButton){
            this.buildAddScreen();
            this.remove(this.menuPanel);
            this.add(this.addPanel);
            this.revalidate();
        
        // Ações para o botão de ver acervo    
        } else if(e.getSource()==this.acervoButton){
            this.buildAcervoScreen();
            this.remove(this.menuPanel);
            this.repaint();
            this.add(this.acervoPanel);
            this.revalidate();
            
        // Ações para o botão de cadastrar cliente
        } else if(e.getSource()==this.cdstClienteButton){
            this.buildCdstClienteScreen();
            this.remove(this.menuPanel);
            this.add(this.cdstClientePanel);
            this.revalidate();
        
        // Ações para o botão de cadastrar funcionário
        } else if(e.getSource()==this.cdstFuncButton){
            this.buildCdstFuncScreen();
            this.remove(this.menuPanel);
            this.add(this.cdstFuncPanel);
            this.revalidate();
        
        // Ações para o botão de empréstimo
        } else if(e.getSource()==this.emprestimoButton){    
            this.buildEmprestimoScreen();
            this.remove(this.menuPanel);
            this.repaint();
            this.add(this.emprestimoPanel);
            this.revalidate();
        
        } else if(e.getSource()==this.confirmaDevButton){
            if(emprestimosAbertos.getSelectedRow()!=-1){
                String id = (String) this.emprestimosAbertos.getValueAt(emprestimosAbertos.getSelectedRow(), 0);
                Emprestimo emp = bd.getEmprestimo(Integer.parseInt(id));
                float total = emp.getData().until(LocalDate.now(), ChronoUnit.DAYS);
                int input = JOptionPane.showConfirmDialog(null, "Realizar devolução?\nTotal: R$ "+total);
                if(input==0){
                    emp.setDevolvido(true);
                    for(Livro l: emp.getLivros()){
                        l.setEmprestado(false);
                    }
                    this.model.removeRow(emprestimosAbertos.getSelectedRow());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um empréstimo.");
            }
        
        // Ações para o botão de remover livro
        } else if(e.getSource()==this.rmvLivroButton){    
            if(tabelaLivros.getSelectedRow()!=-1){
                String isbn = (String) this.tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 2);
                Livro l = bd.getLivro(isbn);
                int input = JOptionPane.showConfirmDialog(null, "Remover "+l.titulo+" do acervo?");
                if(input==0){
                    bd.removeLivro(isbn);
                    this.model.removeRow(tabelaLivros.getSelectedRow());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um livro.");
            }
        
        // Ações para o botão de editar informações de um livro    
        } else if(e.getSource()==this.editaLivroButton){
            if(tabelaLivros.getSelectedRow()!=-1){
                String titulo = (String) this.tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 0);
                String autor = (String) this.tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 1);
                String isbn = (String) this.tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 2);
                String editora = (String) this.tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 3);
                String ano = (String) this.tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 4);
                this.livroAtual = bd.getLivro(isbn);
                this.buildEditaLivroScreen(titulo, autor, isbn, ano, editora);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um livro.");
            }
            
            
        } else if(e.getSource()==this.confirmaEditButton){
            if(inputTitulo.getText().isEmpty() || inputAutor.getText().isEmpty() || inputEditora.getText().isEmpty() ||
                    inputISBN.getText().isEmpty() || inputAno.getText().isEmpty()){
                JOptionPane.showMessageDialog(this.editaLivro, "Nenhuma informação pode ficar em branco");
            } else {
                String titulo = inputTitulo.getText();
                String editora = inputEditora.getText();
                String autor = inputAutor.getText();
                String ISBN = inputISBN.getText();
                int ano =  Integer.parseInt(inputAno.getText().replaceAll("\\.",""));
                int input = JOptionPane.showConfirmDialog(null, "Novos dados:\nTítulo: "+titulo+"\nAutor: "+autor+"\nISBN: "+ISBN+"\nEditora: "
                        +editora+"\nAno de publicação: "+ano+"\nConfirmar alterações?");
                if(input==0){
                    livroAtual.setAnoPublicacao(ano);
                    livroAtual.setAutor(autor);
                    livroAtual.setTitulo(titulo);
                    livroAtual.setISBN(ISBN);
                    livroAtual.setEditora(editora);
                }
                
            }
            
        // Ações para o botão de cancelar adição de livro
        } else if(e.getSource()==this.cancelaButton){
            this.buildMenuScreen(funcionarioAtual.ehGerente());
            this.getContentPane().removeAll();
            this.repaint();
            this.add(this.menuPanel);
            this.revalidate();
        
        // Ações para o botão de confirmar adição de livro
        } else if(e.getSource()==this.confirmaAddButton){
            if(inputTitulo.getText().isEmpty() || inputAutor.getText().isEmpty() || inputEditora.getText().isEmpty() ||
                    inputISBN.getText().isEmpty() || inputAno.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Nenhuma informação pode ficar em branco");
            } else {
                String titulo = inputTitulo.getText();
                String editora = inputEditora.getText();
                String autor = inputAutor.getText();
                String ISBN = inputISBN.getText();
                int ano =  Integer.parseInt(inputAno.getText().replaceAll("\\.",""));  
                boolean resultado = bd.adicionaLivro(titulo, autor, editora, ISBN, ano);
                if(resultado){
                    JOptionPane.showMessageDialog(this, "Livro adicionado ao acervo.");
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    this.getContentPane().removeAll();
                    this.repaint();
                    this.add(this.menuPanel);
                    this.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "Livro já se encontra no acervo.");
                }
            }
        
        // Ações para o botão de confirmar cadastrado de cliente
        } else if(e.getSource()==this.confirmaCdstButton){
            if(inputNome.getText().isEmpty() || inputCPF.getText().isEmpty() || inputEndereco.getText().isEmpty() ||
                    inputCelular.getText().isEmpty() || inputData.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Nenhuma informação pode ficar em branco");
            } else {
                String nome = inputNome.getText();
                String cpf = inputCPF.getText();
                String endereco = inputEndereco.getText();
                String celular = inputCelular.getText();
                String data = inputData.getText();
                boolean resultado = bd.cadastraCliente(nome, cpf, endereco, celular, data);
                if(resultado){
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso.");
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    this.getContentPane().removeAll();
                    this.repaint();
                    this.add(this.menuPanel);
                    this.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente já possui cadastro.");
                }
                
            }
            
        // Ações para o botão de confirmar cadastro de funcionario
        } else if(e.getSource()==this.confirmaCdstButton2){
            if(inputNome.getText().isEmpty() || inputCPF.getText().isEmpty() || inputEndereco.getText().isEmpty() ||
                    inputCelular.getText().isEmpty() || inputData.getText().isEmpty() || inputCargo.getText().isEmpty() || senha.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Nenhuma informação pode ficar em branco");
            } else {
                String nome = inputNome.getText();
                String cpf = inputCPF.getText();
                String endereco = inputEndereco.getText();
                String celular = inputCelular.getText();
                String data = inputData.getText();
                String cargo = inputCargo.getText();
                boolean resultado = bd.cadastraFuncionario(nome, cpf, endereco, celular, data, senha.getText(), cargo);
                if(resultado){
                    JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso.");
                    this.buildMenuScreen(funcionarioAtual.ehGerente());
                    this.getContentPane().removeAll();
                    this.repaint();
                    this.add(this.menuPanel);
                    this.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "Funiconário já possui cadastro.");
                }
                
            }
        
        // Ações para botão de confirmar empréstimo
        } else if(e.getSource()==this.confirmaEmprstButton){
            if(inputCPF.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "O CPF não pode ficar em branco.");
            } else if(inputLivro1.getText().isEmpty() && inputLivro2.getText().isEmpty() && inputLivro3.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Pelo menos 1 livro deve ser emprestado.");
            } else {
                List<Livro> livros = new ArrayList<>();             
                if(!inputLivro1.getText().isEmpty())
                    if(bd.getLivro(inputLivro1.getText())!=null)
                        livros.add(bd.getLivro(inputLivro1.getText()));
                if(!inputLivro2.getText().isEmpty())
                    if(bd.getLivro(inputLivro2.getText())!=null)
                        livros.add(bd.getLivro(inputLivro2.getText()));
                if(!inputLivro3.getText().isEmpty())
                    if(bd.getLivro(inputLivro3.getText())!=null)
                        livros.add(bd.getLivro(inputLivro3.getText()));
                
                if(livros.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Nenhum livro foi encontrado.\nVerifique o ISBN.");
                } else {
                    if(livros.stream().allMatch(l -> l.getEmprestado()==false)){
                        boolean resultado = bd.novoEmprestimo(funcionarioAtual, inputCPF.getText(), livros);
                        if(resultado){
                            JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso.");
                            this.buildMenuScreen(funcionarioAtual.ehGerente());
                            this.getContentPane().removeAll();
                            this.repaint();
                            this.add(this.menuPanel);
                            this.revalidate();
                        } else {
                            JOptionPane.showMessageDialog(this, "Cliente não cadastrado no sistema.");
                        }
                    } else{
                        JOptionPane.showMessageDialog(this, "Alguns dos livros consta como não-devolvido.");
                    }                   
                }    
            }
        }
    }
}
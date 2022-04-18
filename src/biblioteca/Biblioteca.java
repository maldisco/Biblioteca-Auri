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
    private JButton loginButton, logoutButton, clientesButton, funcionariosButton, confirmaDevButton, rmvLivroButton, rmvClienteButton, rmvFuncionarioButton, salvaLivroButton,
            confirmaCdstClienteButton, confirmaEmprestimoButton, confirmaCdstFuncionarioButton, confirmaAddLivroButton, cancelaButton, addLivroButton, emprestimoButton, acervoButton,
            cdstClienteButton, cdstFuncionarioButton, editaLivroButton, editaClienteButton, editaFuncionarioButton, salvaClienteButton, salvaFuncionarioButton;
    private JFrame editaLivro, adicionaLivro, editaCliente, cadastraCliente, editaFuncionario, cadastraFuncionario;
    private JLabel logo;
    private JTable emprestimosAbertos, tabelaLivros, tabelaClientes, tabelaFuncionarios;
    private JPanel loginPanel, menuPanel, editPanel, acervoPanel, clientesPanel, funcionariosPanel, emprestimoPanel;
    private JTextField login, inputTitulo, inputEditora, inputAutor, inputISBN, inputAno, inputNome, inputCPF, inputEndereco, inputCelular, inputData, inputCargo, inputLivro1,
            inputLivro2, inputLivro3, inputPesquisa, inputSenha;
    private JPasswordField senha;
    private TableRowSorter<TableModel> pesquisa;
    private DefaultTableModel model;
    private final Banco bd;
    private Funcionario funcionarioAtual, funcionarioAlterado;
    private Livro livroAlterado;
    private Cliente clienteAlterado;
    
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
        
        // Informações do funcionário atual
        JPanel func = new JPanel();
        func.setBackground(new Color(0x123456));

        JLabel nomeFunc = new JLabel("Logado como "+funcionarioAtual.getNome());
        nomeFunc.setFont(new Font("Verdana", Font.BOLD, 15));
        nomeFunc.setForeground(Color.BLACK);
        func.add(nomeFunc);
        
        logoutButton = new JButton();
        logoutButton.setText("Log out");
        logoutButton.setFont(new Font("Verdana", Font.PLAIN, 10));
        logoutButton.addActionListener(this);
        func.add(logoutButton);
        
        c.gridy = 0;
        c.gridx = 3;
        c.ipady = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTHEAST;
        menuPanel.add(func, c);
       
        // Botão acessar acervo
        this.acervoButton = new JButton();
        acervoButton.setText("Livros");
        acervoButton.setFont(new Font("Verdana", Font.BOLD, 30));
        acervoButton.addActionListener(this);     
        c.insets = new Insets(50, 70, 50, 70);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        menuPanel.add(acervoButton, c);
        
        // Botão acessar clientes
        this.clientesButton = new JButton();
        clientesButton.setText("Clientes");
        clientesButton.setFont(new Font("Verdana", Font.BOLD, 30));
        clientesButton.addActionListener(this);
        c.gridy = 1;
        c.gridx = 1;
        c.gridwidth = 1;
        menuPanel.add(clientesButton, c);
        
        // Botão acessar funcionários
        this.funcionariosButton = new JButton();
        funcionariosButton.setEnabled(ehGerente);
        funcionariosButton.setText("Funcionários");
        funcionariosButton.setFont(new Font("Verdana", Font.BOLD, 30));
        funcionariosButton.addActionListener(this);
        c.gridy = 1;
        c.gridx = 2;
        c.gridwidth = 1;
        menuPanel.add(funcionariosButton, c);
        
        // Botão Emprestar livro
        this.emprestimoButton = new JButton();
        emprestimoButton.setText("Novo Empréstimo");
        emprestimoButton.setFont(new Font("Verdana", Font.BOLD, 30));
        emprestimoButton.addActionListener(this);
        c.gridy = 1;
        c.gridx = 3;
        c.gridwidth = 1;
        menuPanel.add(emprestimoButton, c);
        
        // Lista de empréstimos em aberto
        JPanel emp = new JPanel();
        emp.setBackground(new Color(0x123456));
        emp.setLayout(new BoxLayout(emp, BoxLayout.PAGE_AXIS));
                
        String[] nomeColunas = {"Id", "Nome do cliente", "CPF do cliente", "Nome do funcionário", "Livros emprestados", "Data de empréstimo", "Devolvido"};
        this.model = new DefaultTableModel(nomeColunas, 0);
        for(Emprestimo e: bd.getEmprestimos()){
            model.addRow(e.info());
        }      
        this.emprestimosAbertos = new JTable(model);
        emprestimosAbertos.getColumnModel().getColumn(0).setPreferredWidth(1);    // Tornar a coluna de Id menor
        emprestimosAbertos.getColumnModel().getColumn(4).setPreferredWidth(300);    // Tornar a coluna de livros maior
        emprestimosAbertos.setRowHeight(25);
        emprestimosAbertos.setDefaultEditor(Object.class, null);    // Tornar as células não editáveis                
        this.pesquisa = new TableRowSorter<>(emprestimosAbertos.getModel());        
        emprestimosAbertos.setRowSorter(pesquisa);

        // Barra de pesquisa
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
        
        // Botão de devolução
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
        c.gridy = 2;
        c.gridwidth = 4;
        menuPanel.add(emp, c);
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
        
        // Lista de empréstimos em aberto
        JPanel acervo = new JPanel();
        acervo.setBackground(new Color(0x123456));
        acervo.setLayout(new BoxLayout(acervo, BoxLayout.PAGE_AXIS));
                
        String[] nomeColunas = {"Título", "Autor", "ISBN", "Editora", "Ano de publicação", "Emprestado"};
        this.model = new DefaultTableModel(nomeColunas, 0);
        for(Livro l: bd.getLivros()){
            model.addRow(l.info());
        }
        this.tabelaLivros = new JTable(model);   
        tabelaLivros.setRowHeight(25);
        tabelaLivros.setDefaultEditor(Object.class, null);    // Tornar as células não editáveis          
        this.pesquisa = new TableRowSorter<>(tabelaLivros.getModel());        
        tabelaLivros.setRowSorter(pesquisa);

        // Barra de pesquisa
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
        
        // Botão voltar
        this.cancelaButton = new JButton();
        cancelaButton.setText("Voltar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        cancelaButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cancelaButton);
        
        // Botão adicionar livro
        this.addLivroButton = new JButton();
        addLivroButton.setText("Adicionar");
        addLivroButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        addLivroButton.addActionListener(this);
        addLivroButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(addLivroButton);
        
        // Botão remover livro
        rmvLivroButton = new JButton();
        rmvLivroButton.setText("Remover");
        rmvLivroButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        rmvLivroButton.addActionListener(this);
        rmvLivroButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(rmvLivroButton);
        
        // Botão editar livro
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
        acervoPanel.add(acervo);
    }
    
    /**
     * Constrói tabela de visualização dos clientes
     * com opções de remover e alterar
     */
    public void buildClientesScreen(){
        this.clientesPanel =  new JPanel();
        clientesPanel.setBackground(new Color(0x123456));
        clientesPanel.setBounds(0, 0, 1800, 1000);
        clientesPanel.setLayout(new GridBagLayout());
        
        // Parametros do layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // Lista de clientes
        JPanel clientes = new JPanel();
        clientes.setBackground(new Color(0x123456));
        clientes.setLayout(new BoxLayout(clientes, BoxLayout.PAGE_AXIS));
                
        String[] nomeColunas = {"Nome", "CPF", "Endereço", "Celular", "Data de nascimento", "Quantidade de empréstimos", "Empréstimo em aberto"};
        this.model = new DefaultTableModel(nomeColunas, 0);
        for(Cliente cli: bd.getClientes()){
            model.addRow(cli.info());
        }
        this.tabelaClientes = new JTable(model);   
        tabelaClientes.setRowHeight(25);
        tabelaClientes.setDefaultEditor(Object.class, null);    // Tornar as células não editáveis          
        this.pesquisa = new TableRowSorter<>(tabelaClientes.getModel());        
        tabelaClientes.setRowSorter(pesquisa);

        // Barra de pesquisa
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
                
        // Botão voltar
        this.cancelaButton = new JButton();
        cancelaButton.setText("Voltar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        cancelaButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cancelaButton);
        
        // Botão adicionar cliente
        this.cdstClienteButton = new JButton();
        cdstClienteButton.setText("Cadastrar");
        cdstClienteButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cdstClienteButton.addActionListener(this);
        cdstClienteButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cdstClienteButton);
                
        // Botão editar cliente
        this.editaClienteButton = new JButton();
        editaClienteButton.setText("Editar");
        editaClienteButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        editaClienteButton.addActionListener(this);
        editaClienteButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(editaClienteButton);
      
        // Botão remover cliente 
        rmvClienteButton = new JButton();
        rmvClienteButton.setText("Remover");
        rmvClienteButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        rmvClienteButton.addActionListener(this);
        rmvClienteButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(rmvClienteButton);
  
        clientes.add(inputPesquisa);
        clientes.add(Box.createRigidArea(new Dimension(0, 50)));
        clientes.add(new JScrollPane(tabelaClientes));
        clientes.add(Box.createRigidArea(new Dimension(0, 20)));
        clientes.add(botoes);
        
        c.gridx = 0;
        c.gridy = 1;       
        clientesPanel.add(clientes);
    }
    
    /**
     * Constrói tabela de visualização dos clientes
     * com opções de remover e alterar
     */
    public void buildFuncionariosScreen(){
        this.funcionariosPanel =  new JPanel();
        funcionariosPanel.setBackground(new Color(0x123456));
        funcionariosPanel.setBounds(0, 0, 1800, 1000);
        funcionariosPanel.setLayout(new GridBagLayout());
        
        // Parametros do layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // Lista de funcionários
        JPanel funcionarios = new JPanel();
        funcionarios.setBackground(new Color(0x123456));
        funcionarios.setLayout(new BoxLayout(funcionarios, BoxLayout.PAGE_AXIS));
                
        String[] nomeColunas = {"Nome", "CPF", "Endereço", "Celular", "Data de nascimento", "Cargo", "Senha"};
        this.model = new DefaultTableModel(nomeColunas, 0);
        for(Funcionario func: bd.getFuncionarios()){
            model.addRow(func.info());
        }
        this.tabelaFuncionarios = new JTable(model);   
        tabelaFuncionarios.setRowHeight(25);
        tabelaFuncionarios.setDefaultEditor(Object.class, null);    // Tornar as células não editáveis          
        this.pesquisa = new TableRowSorter<>(tabelaFuncionarios.getModel());        
        tabelaFuncionarios.setRowSorter(pesquisa);

        // Barra de pesquisa
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
        
        // Botão voltar
        this.cancelaButton = new JButton();
        cancelaButton.setText("Voltar");
        cancelaButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cancelaButton.addActionListener(this);
        cancelaButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cancelaButton);
        
        // Botão cadastrar funcionário
        this.cdstFuncionarioButton = new JButton();
        cdstFuncionarioButton.setText("Cadastrar");
        cdstFuncionarioButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        cdstFuncionarioButton.addActionListener(this);
        cdstFuncionarioButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(cdstFuncionarioButton);
                
        // Botão editar funcionário
        this.editaFuncionarioButton = new JButton();
        editaFuncionarioButton.setText("Editar");
        editaFuncionarioButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        editaFuncionarioButton.addActionListener(this);
        editaFuncionarioButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(editaFuncionarioButton);
        
        // Botão remover funcionário   
        rmvFuncionarioButton = new JButton();
        rmvFuncionarioButton.setText("Remover");
        rmvFuncionarioButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        rmvFuncionarioButton.addActionListener(this);
        rmvFuncionarioButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(rmvFuncionarioButton);

        funcionarios.add(inputPesquisa);
        funcionarios.add(Box.createRigidArea(new Dimension(0, 50)));
        funcionarios.add(new JScrollPane(tabelaFuncionarios));
        funcionarios.add(Box.createRigidArea(new Dimension(0, 20)));
        funcionarios.add(botoes);
        
        c.gridx = 0;
        c.gridy = 1;       
        funcionariosPanel.add(funcionarios);
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
        label.setText("Alterar informações do livro");
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
        salvaLivroButton = new JButton();
        salvaLivroButton.setText("Salvar");
        salvaLivroButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        salvaLivroButton.addActionListener(this);
        salvaLivroButton.setPreferredSize(new Dimension(150, 30));
        botoes.add(salvaLivroButton);
        
        // Adiciona os componentes ao painel
        editPanel.add(botoes);
        
        // Adiciona o painel à frame
        editaLivro.add(editPanel);
        // Torna a frame visível
        editaLivro.setVisible(true);
    }
    
    public void buildAdicionaLivroScreen(){
        this.adicionaLivro = new JFrame();
        adicionaLivro.setSize(new Dimension(400,600));
        adicionaLivro.setLocationRelativeTo(null);
        adicionaLivro.setTitle("Adicionar livro");
        
        // Painel de adição de livro
        this.editPanel =  new JPanel();
        editPanel.setBounds(0, 0, 400, 600);
        editPanel.setLayout(new GridLayout(7, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Adicionar livro ao acervo");
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
        inputTitulo = new JTextField();
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
        inputAutor = new JTextField();
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
        inputEditora = new JTextField();
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
        inputISBN = new JTextField();
        inputISBN.setFont(new Font("Arial", Font.PLAIN, 14));
        inputISBN.setPreferredSize(new Dimension(200, 20));
        ISBNLivro.add(inputISBN);
        editPanel.add(ISBNLivro);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        confirmaAddLivroButton = new JButton();
        confirmaAddLivroButton.setText("Adicionar");
        confirmaAddLivroButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        confirmaAddLivroButton.addActionListener(this);
        confirmaAddLivroButton.setPreferredSize(new Dimension(150, 30));
        botoes.add(confirmaAddLivroButton);
        
        // Adiciona os componentes ao painel
        editPanel.add(botoes);
        
        // Adiciona o painel à frame
        adicionaLivro.add(editPanel);
        // Torna a frame visível
        adicionaLivro.setVisible(true);
    }
    
    /**
     * Constrói uma frame para editar as informações de um cliente do banco de dados
     * Cada parametro é um 'placeholder' para as caixas de texto
     * @param nome
     * @param cpf
     * @param endereco
     * @param celular
     * @param dataNascimento
     */
    public void buildEditaClienteScreen(String nome, String cpf, String endereco, String celular, String dataNascimento){
        this.editaCliente = new JFrame();
        editaCliente.setSize(new Dimension(400,600));
        editaCliente.setLocationRelativeTo(null);
        editaCliente.setTitle("Alterar cliente");
        
        // Painel de alteração de dados
        this.editPanel =  new JPanel();
        editPanel.setBounds(0, 0, 400, 600);
        editPanel.setLayout(new GridLayout(7, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Alterar informações do cliente");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        editPanel.add(label);
        
        // Campo de nome do cliente (com label)
        JPanel nomeCliente = new JPanel();
        nomeCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Nome");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        nomeCliente.add(label);
        inputNome = new JTextField(nome);
        inputNome.setFont(new Font("Arial", Font.PLAIN, 14));
        inputNome.setPreferredSize(new Dimension(200, 20));
        nomeCliente.add(inputNome);
        editPanel.add(nomeCliente);
        
        // Campo de CPF do cliente (com label)
        JPanel cpfCliente = new JPanel();
        cpfCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("CPF");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cpfCliente.add(label);
        inputCPF = new JTextField(cpf);
        inputCPF.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCPF.setPreferredSize(new Dimension(200, 20));
        cpfCliente.add(inputCPF);
        editPanel.add(cpfCliente);
        
        // Campo de endereço do cliente (com label)
        JPanel enderecoCliente = new JPanel();
        enderecoCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Endereço");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        enderecoCliente.add(label);        
        inputEndereco = new JTextField(endereco);
        inputEndereco.setFont(new Font("Arial", Font.PLAIN, 14));
        inputEndereco.setPreferredSize(new Dimension(200, 20));
        enderecoCliente.add(inputEndereco);
        editPanel.add(enderecoCliente);
        
        // Campo de celular do cliente (com label)
        JPanel celularCliente = new JPanel();
        celularCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Celular");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        celularCliente.add(label);       
        inputCelular = new JTextField(celular);
        inputCelular.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCelular.setPreferredSize(new Dimension(200, 20));
        celularCliente.add(inputCelular);
        editPanel.add(celularCliente);
        
        // Campo de data de nascimento do cliente (com label)
        JPanel dataNascimentoCliente = new JPanel();
        dataNascimentoCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Data de nascimento");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(150, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        dataNascimentoCliente.add(label);
        inputData = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        inputData.setText(dataNascimento);
        inputData.setFont(new Font("Arial", Font.PLAIN, 14));
        inputData.setPreferredSize(new Dimension(150, 20));
        dataNascimentoCliente.add(inputData);
        editPanel.add(dataNascimentoCliente);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        salvaClienteButton = new JButton();
        salvaClienteButton.setText("Salvar");
        salvaClienteButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        salvaClienteButton.addActionListener(this);
        salvaClienteButton.setPreferredSize(new Dimension(150, 30));
        botoes.add(salvaClienteButton);
        
        // Adiciona os componentes ao painel
        editPanel.add(botoes);
        
        // Adiciona o painel à frame
        editaCliente.add(editPanel);
        // Torna a frame visível
        editaCliente.setVisible(true);
    }

    /**
     * Constrói a tela para cadastro de um novo cliente no sistema
     */
    public void buildCadastraClienteScreen(){
        this.cadastraCliente = new JFrame();
        cadastraCliente.setSize(new Dimension(400,600));
        cadastraCliente.setLocationRelativeTo(null);
        cadastraCliente.setTitle("Cadastrar cliente");
        
        // Painel de adição de livro
        this.editPanel =  new JPanel();
        editPanel.setBounds(0, 0, 400, 600);
        editPanel.setLayout(new GridLayout(7, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Cadastrar novo cliente");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        editPanel.add(label);
        
        // Campo de nome do cliente (com label)
        JPanel nomeCliente = new JPanel();
        nomeCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Nome");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        nomeCliente.add(label);
        inputNome = new JTextField();
        inputNome.setFont(new Font("Arial", Font.PLAIN, 14));
        inputNome.setPreferredSize(new Dimension(200, 20));
        nomeCliente.add(inputNome);
        editPanel.add(nomeCliente);
        
        // Campo de CPF do cliente (com label)
        JPanel cpfCliente = new JPanel();
        cpfCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("CPF");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cpfCliente.add(label);
        inputCPF = new JTextField();
        inputCPF.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCPF.setPreferredSize(new Dimension(200, 20));
        cpfCliente.add(inputCPF);
        editPanel.add(cpfCliente);
        
        // Campo de endereço do cliente (com label)
        JPanel enderecoCliente = new JPanel();
        enderecoCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Endereço");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        enderecoCliente.add(label);        
        inputEndereco = new JTextField();
        inputEndereco.setFont(new Font("Arial", Font.PLAIN, 14));
        inputEndereco.setPreferredSize(new Dimension(200, 20));
        enderecoCliente.add(inputEndereco);
        editPanel.add(enderecoCliente);
        
        // Campo de celular do cliente (com label)
        JPanel celularCliente = new JPanel();
        celularCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Celular");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        celularCliente.add(label);       
        inputCelular = new JTextField();
        inputCelular.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCelular.setPreferredSize(new Dimension(200, 20));
        celularCliente.add(inputCelular);
        editPanel.add(celularCliente);
        
        // Campo de data de nascimento do cliente (com label)
        JPanel dataNascimentoCliente = new JPanel();
        dataNascimentoCliente.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Data de nascimento");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(150, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        dataNascimentoCliente.add(label);
        inputData = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        inputData.setFont(new Font("Arial", Font.PLAIN, 14));
        inputData.setPreferredSize(new Dimension(150, 20));
        dataNascimentoCliente.add(inputData);
        editPanel.add(dataNascimentoCliente);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        confirmaCdstClienteButton = new JButton();
        confirmaCdstClienteButton.setText("Cadastrar");
        confirmaCdstClienteButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        confirmaCdstClienteButton.addActionListener(this);
        confirmaCdstClienteButton.setPreferredSize(new Dimension(150, 30));
        botoes.add(confirmaCdstClienteButton);
        
        // Adiciona os componentes ao painel
        editPanel.add(botoes);
        
        // Adiciona o painel à frame
        cadastraCliente.add(editPanel);
        // Torna a frame visível
        cadastraCliente.setVisible(true);
    }
    
    /**
     * Constrói tela para edição de um funcionário do banco
     * Todos os parametros são 'placeholders' para as caixas de texto
     * @param nome
     * @param cpf
     * @param endereco
     * @param celular
     * @param dataNascimento
     * @param cargo
     * @param senha 
     */
    public void buildEditaFuncionarioScreen(String nome, String cpf, String endereco, String celular, String dataNascimento, String cargo, String senha){
        this.editaFuncionario = new JFrame();
        editaFuncionario.setSize(new Dimension(400,600));
        editaFuncionario.setLocationRelativeTo(null);
        editaFuncionario.setTitle("Alterar funcionário");
        
        // Painel de alteração de dados
        this.editPanel =  new JPanel();
        editPanel.setBounds(0, 0, 400, 600);
        editPanel.setLayout(new GridLayout(9, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Alterar funcionário");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        editPanel.add(label);
        
        // Campo de nome do funcionario (com label)
        JPanel nomeFuncionario = new JPanel();
        nomeFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Nome");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        nomeFuncionario.add(label);
        inputNome = new JTextField(nome);
        inputNome.setFont(new Font("Arial", Font.PLAIN, 14));
        inputNome.setPreferredSize(new Dimension(200, 20));
        nomeFuncionario.add(inputNome);
        editPanel.add(nomeFuncionario);
        
        // Campo de CPF do funcionário (com label)
        JPanel cpfFuncionario = new JPanel();
        cpfFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("CPF");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cpfFuncionario.add(label);
        inputCPF = new JTextField(cpf);
        inputCPF.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCPF.setPreferredSize(new Dimension(200, 20));
        cpfFuncionario.add(inputCPF);
        editPanel.add(cpfFuncionario);
        
        // Campo de endereço do funcionário (com label)
        JPanel enderecoFuncionario = new JPanel();
        enderecoFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Endereço");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        enderecoFuncionario.add(label);        
        inputEndereco = new JTextField(endereco);
        inputEndereco.setFont(new Font("Arial", Font.PLAIN, 14));
        inputEndereco.setPreferredSize(new Dimension(200, 20));
        enderecoFuncionario.add(inputEndereco);
        editPanel.add(enderecoFuncionario);
        
        // Campo de celular do funcionário (com label)
        JPanel celularFuncionario = new JPanel();
        celularFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Celular");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        celularFuncionario.add(label);       
        inputCelular = new JTextField(celular);
        inputCelular.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCelular.setPreferredSize(new Dimension(200, 20));
        celularFuncionario.add(inputCelular);
        editPanel.add(celularFuncionario);
        
        // Campo de data de nascimento do funcionário (com label)
        JPanel dataNascimentoFuncionario = new JPanel();
        dataNascimentoFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Data de nascimento");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(150, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        dataNascimentoFuncionario.add(label);
        inputData = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        inputData.setText(dataNascimento);
        inputData.setFont(new Font("Arial", Font.PLAIN, 14));
        inputData.setPreferredSize(new Dimension(150, 20));
        dataNascimentoFuncionario.add(inputData);
        editPanel.add(dataNascimentoFuncionario);
        
        // Campo de celular do funcionário (com label)
        JPanel cargoFuncionario = new JPanel();
        cargoFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Cargo");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cargoFuncionario.add(label);       
        inputCargo = new JTextField(cargo);
        inputCargo.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCargo.setPreferredSize(new Dimension(200, 20));
        cargoFuncionario.add(inputCargo);
        editPanel.add(cargoFuncionario);
        
        // Campo de senha do funcionário (com label)
        JPanel senhaFuncionario = new JPanel();
        senhaFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Senha");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        senhaFuncionario.add(label);       
        inputSenha = new JTextField(senha);
        inputSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        inputSenha.setPreferredSize(new Dimension(200, 20));
        senhaFuncionario.add(inputSenha);
        editPanel.add(senhaFuncionario);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        salvaFuncionarioButton = new JButton();
        salvaFuncionarioButton.setText("Salvar");
        salvaFuncionarioButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        salvaFuncionarioButton.addActionListener(this);
        salvaFuncionarioButton.setPreferredSize(new Dimension(150, 30));
        botoes.add(salvaFuncionarioButton);
        
        // Adiciona os componentes ao painel
        editPanel.add(botoes);
        
        // Adiciona o painel à frame
        editaFuncionario.add(editPanel);
        
        // Torna a frame visível
        editaFuncionario.setVisible(true);
    }

    /**
     * Constrói tela para cadastro de um novo funcionário ao sistema
     */
    public void buildCdstFuncionarioScreen(){
        this.cadastraFuncionario = new JFrame();
        cadastraFuncionario.setSize(new Dimension(400,600));
        cadastraFuncionario.setLocationRelativeTo(null);
        cadastraFuncionario.setTitle("Cadastrar funcionário");
        
        // Painel de alteração de dados
        this.editPanel =  new JPanel();
        editPanel.setBounds(0, 0, 400, 600);
        editPanel.setLayout(new GridLayout(9, 1));
        
        // Título da página
        JLabel label = new JLabel();
        label.setText("Cadastrar novo funcionário");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        editPanel.add(label);
        
        // Campo de nome do funcionario (com label)
        JPanel nomeFuncionario = new JPanel();
        nomeFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Nome");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        nomeFuncionario.add(label);
        inputNome = new JTextField();
        inputNome.setFont(new Font("Arial", Font.PLAIN, 14));
        inputNome.setPreferredSize(new Dimension(200, 20));
        nomeFuncionario.add(inputNome);
        editPanel.add(nomeFuncionario);
        
        // Campo de CPF do funcionário (com label)
        JPanel cpfFuncionario = new JPanel();
        cpfFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("CPF");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cpfFuncionario.add(label);
        inputCPF = new JTextField();
        inputCPF.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCPF.setPreferredSize(new Dimension(200, 20));
        cpfFuncionario.add(inputCPF);
        editPanel.add(cpfFuncionario);
        
        // Campo de endereço do funcionário (com label)
        JPanel enderecoFuncionario = new JPanel();
        enderecoFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Endereço");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        enderecoFuncionario.add(label);        
        inputEndereco = new JTextField();
        inputEndereco.setFont(new Font("Arial", Font.PLAIN, 14));
        inputEndereco.setPreferredSize(new Dimension(200, 20));
        enderecoFuncionario.add(inputEndereco);
        editPanel.add(enderecoFuncionario);
        
        // Campo de celular do funcionário (com label)
        JPanel celularFuncionario = new JPanel();
        celularFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Celular");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        celularFuncionario.add(label);       
        inputCelular = new JTextField();
        inputCelular.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCelular.setPreferredSize(new Dimension(200, 20));
        celularFuncionario.add(inputCelular);
        editPanel.add(celularFuncionario);
        
        // Campo de data de nascimento do funcionário (com label)
        JPanel dataNascimentoFuncionario = new JPanel();
        dataNascimentoFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Data de nascimento");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(150, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        dataNascimentoFuncionario.add(label);
        inputData = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        inputData.setFont(new Font("Arial", Font.PLAIN, 14));
        inputData.setPreferredSize(new Dimension(150, 20));
        dataNascimentoFuncionario.add(inputData);
        editPanel.add(dataNascimentoFuncionario);
        
        // Campo de celular do funcionário (com label)
        JPanel cargoFuncionario = new JPanel();
        cargoFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Cargo");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        cargoFuncionario.add(label);       
        inputCargo = new JTextField();
        inputCargo.setFont(new Font("Arial", Font.PLAIN, 14));
        inputCargo.setPreferredSize(new Dimension(200, 20));
        cargoFuncionario.add(inputCargo);
        editPanel.add(cargoFuncionario);
        
        // Campo de senha do funcionário (com label)
        JPanel senhaFuncionario = new JPanel();
        senhaFuncionario.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        label = new JLabel();
        label.setText("Senha");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setPreferredSize(new Dimension(100, 20));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        senhaFuncionario.add(label);       
        inputSenha = new JTextField();
        inputSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        inputSenha.setPreferredSize(new Dimension(200, 20));
        senhaFuncionario.add(inputSenha);
        editPanel.add(senhaFuncionario);
        
        // Botão confirmar
        JPanel botoes = new JPanel();
        botoes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        confirmaCdstFuncionarioButton = new JButton();
        confirmaCdstFuncionarioButton.setText("Cadastrar");
        confirmaCdstFuncionarioButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        confirmaCdstFuncionarioButton.addActionListener(this);
        confirmaCdstFuncionarioButton.setPreferredSize(new Dimension(150, 30));
        botoes.add(confirmaCdstFuncionarioButton);
        
        // Adiciona os componentes ao painel
        editPanel.add(botoes);
        
        // Adiciona o painel à frame
        cadastraFuncionario.add(editPanel);
        // Torna a frame visível
        
        cadastraFuncionario.setVisible(true);
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
        confirmaEmprestimoButton = new JButton();
        confirmaEmprestimoButton.setText("Confirmar");
        confirmaEmprestimoButton.setFont(new Font("Verdana", Font.PLAIN, 30));
        confirmaEmprestimoButton.addActionListener(this);
        confirmaEmprestimoButton.setPreferredSize(new Dimension(200, 50));
        botoes.add(confirmaEmprestimoButton);
                
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
                    this.repaint();
                    this.add(this.menuPanel);
                    this.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "Senha incorreta.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
            }
        
        // Ações pra o botão de logout
        } else if(e.getSource()==this.logoutButton){
            funcionarioAtual = null;
            this.buildLoginScreen();
            this.remove(menuPanel);
            this.repaint();
            this.add(loginPanel);
            this.revalidate();      
            
        // Ações para o botão de cancelar (volta para o menu principal)
        } else if(e.getSource()==this.cancelaButton){
            this.buildMenuScreen(funcionarioAtual.ehGerente());
            this.getContentPane().removeAll();
            this.repaint();
            this.add(this.menuPanel);
            this.revalidate();    
            
        // Ações para o botão de ver acervo    
        } else if(e.getSource()==this.acervoButton){
            this.buildAcervoScreen();
            this.remove(this.menuPanel);
            this.repaint();
            this.add(this.acervoPanel);
            this.revalidate();
        
        // Ações para o botão de ver clientes    
        } else if(e.getSource()==this.clientesButton){
            this.buildClientesScreen();
            this.remove(this.menuPanel);
            this.repaint();
            this.add(this.clientesPanel);
            this.revalidate();
        
        // Ações para o botão de cadastrar funcionário
        } else if(e.getSource()==this.funcionariosButton){
            this.buildFuncionariosScreen();
            this.remove(this.menuPanel);
            this.repaint();
            this.add(this.funcionariosPanel);
            this.revalidate();
        
        // Ações para o botão de empréstimo
        } else if(e.getSource()==this.emprestimoButton){    
            this.buildEmprestimoScreen();
            this.remove(this.menuPanel);
            this.repaint();
            this.add(this.emprestimoPanel);
            this.revalidate();
        
        // Ações para o botão de confirmar devolução
        } else if(e.getSource()==this.confirmaDevButton){
            if(emprestimosAbertos.getSelectedRow()!=-1){
                String devolvido = (String) this.emprestimosAbertos.getValueAt(emprestimosAbertos.getSelectedRow(), 6);
                if(devolvido.equals("Não")){
                    String id = (String) this.emprestimosAbertos.getValueAt(emprestimosAbertos.getSelectedRow(), 0);
                    Cliente c = bd.getCliente((String) emprestimosAbertos.getValueAt(emprestimosAbertos.getSelectedRow(), 2));
                    int qtdEmprestimos = c.getQtdEmprestimos();
                    Emprestimo emp = bd.getEmprestimo(Integer.parseInt(id));
                    if(qtdEmprestimos>0 && qtdEmprestimos%5==0){
                        int input = JOptionPane.showConfirmDialog(null, "Cada quinto empréstimo sai gratuitamente :) \nRealizar devolução?");
                        if(input==0){
                            emp.setDevolvido(true);
                            c.setEmprestimoAberto(false);
                            for(Livro l: emp.getLivros()){
                                l.setEmprestado(false);
                            }
                            this.model.removeRow(emprestimosAbertos.getSelectedRow());
                        }
                    } else {
                        float total = emp.getData().until(LocalDate.now(), ChronoUnit.DAYS);
                        int input = JOptionPane.showConfirmDialog(null, "Realizar devolução?\nTotal: R$ "+total);
                        if(input==0){
                            emp.setDevolvido(true);
                            c.setEmprestimoAberto(false);
                            for(Livro l: emp.getLivros()){
                                l.setEmprestado(false);
                            } 
                            this.model.removeRow(emprestimosAbertos.getSelectedRow());
                            this.model.addRow(emp.info());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Este empréstimo ja foi devolvido");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um empréstimo.");
            } 
        
        // Ações para o botão de adicionar livro
        } else if(e.getSource()==this.addLivroButton){
           this.buildAdicionaLivroScreen();
         
        // Ações para o botão de confirmar cadastro de cliente
        } else if(e.getSource()==this.confirmaAddLivroButton){
            if(inputTitulo.getText().isEmpty() || inputAutor.getText().isEmpty() || inputEditora.getText().isEmpty() ||
                    inputISBN.getText().isEmpty() || inputAno.getText().isEmpty()){
                JOptionPane.showMessageDialog(this.adicionaLivro, "Nenhuma informação pode ficar em branco");
            } else {
                String titulo = inputTitulo.getText();
                String editora = inputEditora.getText();
                String autor = inputAutor.getText();
                String ISBN = inputISBN.getText();
                int ano =  Integer.parseInt(inputAno.getText().replaceAll("\\.",""));
                int input = JOptionPane.showConfirmDialog(null, "Novo livro:\nTítulo: "+titulo+"\nAutor: "+autor+"\nISBN: "+ISBN+"\nEditora: "
                        +editora+"\nAno de publicação: "+ano+"\nConfirmar adição?");
                if(input==0){
                    boolean resultado = bd.adicionaLivro(titulo, autor, editora, ISBN, ano);
                    if (resultado) {
                        JOptionPane.showMessageDialog(this.adicionaLivro, "Livro "+titulo+" adicionado ao acervo.");
                    } else {
                        JOptionPane.showMessageDialog(this.adicionaLivro, "O ISBN "+ISBN+" já está registrado no sistema.");
                    }
                }
                
            }     
            
        // Ações para o botão de remover livro
        } else if(e.getSource()==this.rmvLivroButton){    
            if(tabelaLivros.getSelectedRow()!=-1){
                String isbn = (String) this.tabelaLivros.getValueAt(tabelaLivros.getSelectedRow(), 2);
                Livro l = bd.getLivro(isbn);
                if(l.getEmprestado()){
                    JOptionPane.showMessageDialog(this, "Este livro não pode ser removido pois está emprestado.");
                } else {
                    int input = JOptionPane.showConfirmDialog(null, "Remover "+l.titulo+" do acervo?");
                    if(input==0){
                        bd.removeLivro(l);
                        this.model.removeRow(tabelaLivros.getSelectedRow());
                    }
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
                this.livroAlterado = bd.getLivro(isbn);
                this.buildEditaLivroScreen(titulo, autor, isbn, ano, editora);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um livro.");
            }
                    
        // Ações para botão de salvar alterações de um livro   
        } else if(e.getSource()==this.salvaLivroButton){
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
                    livroAlterado.setAnoPublicacao(ano);
                    livroAlterado.setAutor(autor);
                    livroAlterado.setTitulo(titulo);
                    livroAlterado.setISBN(ISBN);
                    livroAlterado.setEditora(editora);
                }
                
            }
        
        // Ações para o botão de cadastrar cliente
        } else if(e.getSource()==this.cdstClienteButton){
            this.buildCadastraClienteScreen();               
            
        // Ações para o botão de confirmar cadastro de cliente
        } else if(e.getSource()==this.confirmaCdstClienteButton){
            if(inputNome.getText().isEmpty() || inputCPF.getText().isEmpty() || inputCelular.getText().isEmpty()
                    || inputEndereco.getText().isEmpty() || inputData.getText().isEmpty()){
                JOptionPane.showMessageDialog(this.cadastraCliente, "Nenhuma informação pode ficar em branco");
            } else {
                String nome = inputNome.getText();
                String CPF = inputCPF.getText();
                String endereco = inputEndereco.getText();
                String celular = inputCelular.getText();
                String dataNascimento = inputData.getText();
                
                int input = JOptionPane.showConfirmDialog(null, "Novo cliente:\nNome: "+nome+"\nCPF: "+CPF+"\nEndereço: "+endereco+"\nCelular: "
                        +celular+"\nData de nascimento: "+dataNascimento+"\nConfirmar cadastro?");
                if(input==0){
                    boolean resultado = bd.cadastraCliente(nome, CPF, endereco, celular, dataNascimento);
                    if(resultado){
                        JOptionPane.showMessageDialog(this.cadastraCliente, "Cliente "+nome+" cadastrado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(this.cadastraCliente, "O CPF "+CPF+" já está cadastrado no sistema.");
                    }
                }
            }  
            
        // Ações para o botão de remover cliente
        } else if(e.getSource()==this.rmvClienteButton){
            if(tabelaClientes.getSelectedRow()!=-1){
                String cpf = (String) this.tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 1);
                Cliente c = bd.getCliente(cpf);
                if(c.getEmprestimoAberto()){
                    JOptionPane.showMessageDialog(this, "Este cliente não pode ser removido pois possui empréstimo em aberto.");
                } else {
                    int input = JOptionPane.showConfirmDialog(null, "Remover "+c.nome+" do banco de clientes?");
                    if(input==0){
                        bd.removeCliente(c);
                        this.model.removeRow(tabelaClientes.getSelectedRow());
                    }
                }    
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            }
            
        // Ações para o botão de editar um cliente    
        } else if(e.getSource()==this.editaClienteButton){
            if(tabelaClientes.getSelectedRow()!=-1){
                String nome = (String) this.tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 0);
                String CPF = (String) this.tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 1);
                String endereco = (String) this.tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 2);
                String celular = (String) this.tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 3);
                String data = (String) this.tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 4);
                this.clienteAlterado = bd.getCliente(CPF);
                this.buildEditaClienteScreen(nome, CPF, endereco, celular, data);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            }
        
        // Ações para o botão de salvar alterações de um cliente    
        } else if(e.getSource()==this.salvaClienteButton){
            if(inputNome.getText().isEmpty() || inputCPF.getText().isEmpty() || inputCelular.getText().isEmpty()
                    || inputEndereco.getText().isEmpty() || inputData.getText().isEmpty()){
                JOptionPane.showMessageDialog(this.editaCliente, "Nenhuma informação pode ficar em branco");
            } else {
                String nome = inputNome.getText();
                String CPF = inputCPF.getText();
                String endereco = inputEndereco.getText();
                String celular = inputCelular.getText();
                String dataNascimento = inputData.getText();
                
                int input = JOptionPane.showConfirmDialog(null, "Novos dados:\nNome: "+nome+"\nCPF: "+CPF+"\nEndereço: "+endereco+"\nCelular: "
                        +celular+"\nData de nascimento: "+dataNascimento+"\nConfirmar alterações?");
                if(input==0){
                    clienteAlterado.setNome(nome);
                    clienteAlterado.setEndereco(endereco);
                    clienteAlterado.setCpf(CPF);
                    clienteAlterado.setCelular(celular);
                    clienteAlterado.setDataNascimento(dataNascimento);
                }
            }
             
        // Ações para o botão de cadastrar funcionário
        } else if(e.getSource()==this.cdstFuncionarioButton){
            this.buildCdstFuncionarioScreen();
                    
        // Ações para o botão de confirmar cadastro de funcionario
        } else if(e.getSource()==this.confirmaCdstFuncionarioButton){
            if(inputNome.getText().isEmpty() || inputCPF.getText().isEmpty() || inputCelular.getText().isEmpty()
                    || inputEndereco.getText().isEmpty() || inputData.getText().isEmpty() || inputCargo.getText().isEmpty() || senha.getText().isEmpty()){
                JOptionPane.showMessageDialog(this.cadastraFuncionario, "Nenhuma informação pode ficar em branco");
            } else {
                String nome = inputNome.getText();
                String CPF = inputCPF.getText();
                String endereco = inputEndereco.getText();
                String celular = inputCelular.getText();
                String dataNascimento = inputData.getText();
                String cargo = inputCargo.getText();
                String senha = inputSenha.getText();
                
                int input = JOptionPane.showConfirmDialog(null, "Novo funcionário:\nNome: "+nome+"\nCPF: "+CPF+"\nEndereço: "+endereco+"\nCelular: "
                        +celular+"\nData de nascimento: "+dataNascimento+"\nCargo: "+cargo+"\nSenha: "+senha+"\nConfirmar cadastro?");
                if(input==0){
                    boolean resultado = bd.cadastraFuncionario(nome, CPF, endereco, celular, dataNascimento, senha, cargo);
                    if(resultado){
                        JOptionPane.showMessageDialog(this.cadastraFuncionario, "Funcionário "+nome+" cadastrado com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(this.cadastraFuncionario, "O CPF "+CPF+" já está cadastrado no sistema.");
                    }
                }
            }    
        
        // Ações para o botão de remover funcionário
        } else if(e.getSource()==this.rmvFuncionarioButton){
            if(tabelaFuncionarios.getSelectedRow()!=-1){
                String cpf = (String) this.tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 1);
                Funcionario f = bd.getFuncionario(cpf);
                if(bd.getFuncionarios().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Pelo menos 1 (um) funcionário deve ficar cadastrado.");
                } else {
                    int input = JOptionPane.showConfirmDialog(null, "Remover "+f.nome+" do banco de funcionários?");
                    if(input==0){
                        bd.removeFuncionario(f);
                        this.model.removeRow(tabelaFuncionarios.getSelectedRow());
                    }
                }    
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário.");
            }
            
        // Ações para o botão de editar um funcionário    
        } else if(e.getSource()==this.editaFuncionarioButton){
            if(tabelaFuncionarios.getSelectedRow()!=-1){
                String nome = (String) this.tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 0);
                String CPF = (String) this.tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 1);
                String endereco = (String) this.tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 2);
                String celular = (String) this.tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 3);
                String data = (String) this.tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 4);
                String cargo = (String) this.tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 5);
                String senha = (String) this.tabelaFuncionarios.getValueAt(tabelaFuncionarios.getSelectedRow(), 6);
                        
                this.funcionarioAlterado = bd.getFuncionario(CPF);
                this.buildEditaFuncionarioScreen(nome, CPF, endereco, celular, data, cargo, senha);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário.");
            }
            
        // Ações para o botão de salvar alterações de um cliente    
        } else if(e.getSource()==this.salvaFuncionarioButton){
            if(inputNome.getText().isEmpty() || inputCPF.getText().isEmpty() || inputCelular.getText().isEmpty()
                    || inputEndereco.getText().isEmpty() || inputData.getText().isEmpty() || inputCargo.getText().isEmpty() || inputSenha.getText().isEmpty()){
                JOptionPane.showMessageDialog(this.editaFuncionario, "Nenhuma informação pode ficar em branco");
            } else {
                String nome = inputNome.getText();
                String CPF = inputCPF.getText();
                String endereco = inputEndereco.getText();
                String celular = inputCelular.getText();
                String dataNascimento = inputData.getText();
                String cargo = inputCargo.getText();
                String senha = inputSenha.getText();
                
                int input = JOptionPane.showConfirmDialog(null, "Novos dados:\nNome: "+nome+"\nCPF: "+CPF+"\nEndereço: "+endereco+"\nCelular: "
                        +celular+"\nData de nascimento: "+dataNascimento+"\nCargo: "+cargo+"\nSenha: "+senha+"\nConfirmar alterações?");
                if(input==0){
                    funcionarioAlterado.setNome(nome);
                    funcionarioAlterado.setEndereco(endereco);
                    funcionarioAlterado.setCpf(CPF);
                    funcionarioAlterado.setCelular(celular);
                    funcionarioAlterado.setDataNascimento(dataNascimento);
                }
            } 
            
        // Ações para botão de confirmar empréstimo
        } else if(e.getSource()==this.confirmaEmprestimoButton){
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
                        Cliente cli = bd.getCliente(inputCPF.getText());
                        if(cli!=null){
                            if(!cli.emprestimoAberto){
                                bd.novoEmprestimo(funcionarioAtual, cli, livros);
                                JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso.");
                                this.buildMenuScreen(funcionarioAtual.ehGerente());
                                this.getContentPane().removeAll();
                                this.repaint();
                                this.add(this.menuPanel);
                                this.revalidate();
                            } else {
                                JOptionPane.showMessageDialog(this, "Cliente já possui empréstimo aberto.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Cliente não cadastrado no sistema.");
                        }
                    } else{
                        JOptionPane.showMessageDialog(this, "Algum dos livros consta como não-devolvido.");
                    }                   
                }    
            }
        }
    }
}
package biblioteca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Utilizada para gerenciar o banco de dados.
 */
public class Banco {
    private final List<Emprestimo> historico;
    private final List<Livro> livros;
    private final List<Cliente> clientes;
    private final List<Funcionario> funcionarios;
    
    public Banco(){
        historico = new ArrayList<>();
        livros = new ArrayList<>();
        clientes = new ArrayList<>();
        funcionarios = new ArrayList<>();
    }
    
    /**
     * Encontra um livro a partir do seu código ISBN
     * @param ISBN
     * @return Livro
     */
    public Livro getLivro (String ISBN) {
        return livros.stream().filter(livro -> ISBN.equals(livro.getISBN())).findFirst().orElse(null);
    }
    
    /**
     * Encontra um cliente a partir do seu CPF
     * @param CPF
     * @return Cliente
     */
    public Cliente getCliente (String CPF) {
        return clientes.stream().filter(cliente -> CPF.equals(cliente.getCPF())).findFirst().orElse(null);
    }
    
    /**
     * Encontra um funcionário a partir do seu CPF
     * @param CPF
     * @return Funcionario
     */
    public Funcionario getFuncionario (String CPF) {
        return funcionarios.stream().filter(func -> CPF.equals(func.getCPF())).findFirst().orElse(null);
    }
    
    public Emprestimo getEmprestimo (int id) {
        return historico.stream().filter(emp -> id == emp.getId()).findFirst().orElse(null);
    }
    
    /**
     * Encontra todos os empréstimos ainda em aberto
     * @return List
     */
    public List<Emprestimo> emprestimosEmAberto(){
        return historico.stream().filter(emprestimo -> emprestimo.devolvido==false).collect(Collectors.toList());
    }
    
    public List<Livro> getAcervo(){
        return livros;
    }
    
    /**
     * Adiciona um livro ao acervo, caso ele ainda não esteja.
     * @param titulo
     * @param autor
     * @param ISBN
     * @param editora
     * @param anoPublicacao
     * @return boolean (sucesso ou falha)
     */
    public boolean adicionaLivro(String titulo, String autor, String editora, String ISBN, int anoPublicacao){
        if(getLivro(ISBN)!=null)
            return false;
        Livro l = new Livro(titulo, autor, editora, ISBN,  anoPublicacao);
        livros.add(l);
        return true;
    }
    
    /**
     * Remove um livro do acervo, caso ele esteja
     * @param ISBN
     * @return boolean (sucesso ou falha)
     */
    public boolean removeLivro(String ISBN){
        Livro l = getLivro(ISBN);
        if(l==null)
            return false;
        livros.remove(l);
        return true;          
    }
    
    /**
     * Cadastra um cliente no banco, caso não esteja cadastrado
     * @param nome
     * @param cpf
     * @param endereco
     * @param celular
     * @param dataNascimento 
     * @return boolean (sucesso ou falha)
     */
    public boolean cadastraCliente(String nome, String cpf, String endereco, String celular, String dataNascimento){
        if(getCliente(cpf)!= null)
            return false;
        Cliente c = new Cliente(nome, cpf, endereco, celular, dataNascimento);
        clientes.add(c);
        return true;
    }
    
    /**
     * Cadastra um funcionário no banco, caso não esteja cadastrado
     * @param nome
     * @param cpf
     * @param endereco
     * @param celular
     * @param dataNascimento
     * @param senha
     * @param cargo 
     * @return boolean (sucesso ou falha)
     */
    public boolean cadastraFuncionario(String nome, String cpf, String endereco, String celular, String dataNascimento, String senha, String cargo){
        if(getFuncionario(cpf)!=null)
            return false;
        Funcionario f = new Funcionario(nome, cpf, endereco, celular, dataNascimento, senha, cargo);
        funcionarios.add(f);
        return true;
    }
    
    /**
     * Registra um novo empréstimo
     * @param funcionario
     * @param cpf
     * @param livros
     * @return boolean (sucesso ou falha)
     */
    public boolean novoEmprestimo(Funcionario funcionario, String cpf, List<Livro> livros){
        if(getCliente(cpf)==null)
            return false;
        Cliente c = getCliente(cpf);
        Emprestimo e = new Emprestimo(historico.size(), funcionario, c, livros);
        historico.add(e);
        return true;
    }
    
    /**
     * método de teste (checar se os dados estão sendo carregados corretamente
     * EXCLUIR
     */
    public void mostraDados(){
        for(Funcionario f: funcionarios){
            System.out.println(f.toString());
        }
        
        for(Livro l: livros){
            System.out.println(l.toString());
        }
        
        for(Cliente c: clientes){
            System.out.println(c.toString());
        }
    }
    
    /**
     * Carrega os dados dos arquivos para a memória do programa
     */
    public void carregaDados(){
        File funcs =  new File("data/funcionarios.txt");
        File cli = new File("data/clientes.txt");
        File liv = new File("data/livros.txt");
        File emprestimos = new File("data/emprestimos.txt");
        
        try {
            Scanner reader = new Scanner(funcs);
            Scanner reader2 = new Scanner(cli);
            Scanner reader3 = new Scanner(liv);
            Scanner reader4 = new Scanner(emprestimos);
            
            while(reader.hasNextLine()){
                String data[] = reader.nextLine().split(",");
                Funcionario f = new Funcionario(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                funcionarios.add(f);
            }
            
            while(reader2.hasNextLine()){
                String data[] = reader2.nextLine().split(",");
                Cliente c = new Cliente(data[0], data[1], data[2], data[3], data[4], Boolean.parseBoolean(data[5]), Integer.parseInt(data[6]));
                clientes.add(c);
            }
            
            while(reader3.hasNextLine()){
                String data[] = reader3.nextLine().split(",");
                Livro l = new Livro(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Boolean.parseBoolean(data[5]));
                livros.add(l);
            }
            
            while(reader4.hasNextLine()){
                String data[] =  reader4.nextLine().split(",");
                String ISBNs[] = data[4].split("/");
                List<Livro> l = new ArrayList<>();
                for(String isbn: ISBNs){
                    Livro livro =  getLivro(isbn);
                    livro.setEmprestado(true);
                    l.add(livro);
                }
                
                Emprestimo e = new Emprestimo(Integer.parseInt(data[0]), Boolean.parseBoolean(data[1]), this.getFuncionario(data[2]), this.getCliente(data[3]), l, data[5]);    
                historico.add(e);
            }
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Guarda os dados da memória do programa para os arquivos.
     */
    public void guardaDados(){
        try {
            FileWriter funcs =  new FileWriter("data/funcionarios.txt");
            FileWriter cli = new FileWriter("data/clientes.txt");
            FileWriter acervo = new FileWriter("data/livros.txt");
            FileWriter emprestimos =  new FileWriter("data/emprestimos.txt");            
            
            for(Funcionario f: funcionarios){
                funcs.write(f.toString()+"\n");
            }
            funcs.close();
            
            for(Cliente c: clientes){
                cli.write(c.toString()+"\n");
            }
            cli.close();
            
            for(Livro l: livros){
                acervo.write(l.toString()+"\n");
            }
            acervo.close();
            
            for(Emprestimo e: historico){
                emprestimos.write(e.toString()+"\n");
            }
            emprestimos.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}

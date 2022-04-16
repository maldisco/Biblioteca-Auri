package biblioteca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilizada para gerenciar o banco de dados.
 * Possui como atributos listas de empréstimos, livros, clientes e funcionários
 */
class Banco {
    private final List<Emprestimo> emprestimos;
    private final List<Livro> livros;
    private final List<Cliente> clientes;
    private final List<Funcionario> funcionarios;
    
    public Banco(){
        emprestimos = new ArrayList<>();
        livros = new ArrayList<>();
        clientes = new ArrayList<>();
        funcionarios = new ArrayList<>();
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public List<Cliente> getClientes(){
        return clientes;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    
    public List<Emprestimo> getEmprestimos(){
        return emprestimos;
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
    
    /**
     * Encontra um empréstimo a partir do seu ID
     * @param id
     * @return Emprestimo
     */
    public Emprestimo getEmprestimo (int id) {
        return emprestimos.stream().filter(emp -> id == emp.getId()).findFirst().orElse(null);
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
     * @param l
     */
    public void removeLivro(Livro l){
        this.livros.remove(l);
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
     * Remove um cliente do banco
     * @param c 
     */
    public void removeCliente(Cliente c){
        this.clientes.remove(c);
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
     * Remove um funcionário do banco
     * @param f 
     */
    public void removeFuncionario(Funcionario f){
        this.funcionarios.remove(f);
    }
    
    /**
     * Registra um novo empréstimo
     * @param funcionario
     * @param cliente
     * @param livros
     */
    public void novoEmprestimo(Funcionario funcionario, Cliente cliente, List<Livro> livros){        
        cliente.setEmprestimoAberto(true);
        cliente.setQtdEmprestimos(cliente.getQtdEmprestimos()+1);
                
        for(Livro l: livros){
            l.setEmprestado(true);
        }

        emprestimos.add(new Emprestimo(emprestimos.size(), funcionario, cliente, livros));
    }
    
    /**
     * Carrega os dados dos arquivos para a memória do programa
     */
    public void carregaDados(){
        File funcs =  new File("data/funcionarios.txt");
        File cli = new File("data/clientes.txt");
        File liv = new File("data/livros.txt");
        File emps = new File("data/emprestimos.txt");
        
        try {
            Scanner reader = new Scanner(funcs);
            Scanner reader2 = new Scanner(cli);
            Scanner reader3 = new Scanner(liv);
            Scanner reader4 = new Scanner(emps);
            
            while(reader.hasNextLine()){
                String data[] = reader.nextLine().split(",");
                Funcionario f = new Funcionario(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                funcionarios.add(f);
            }
            
            while(reader2.hasNextLine()){
                String data[] = reader2.nextLine().split(",");
                Cliente c = new Cliente(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]));
                clientes.add(c);
            }
            
            while(reader3.hasNextLine()){
                String data[] = reader3.nextLine().split(",");
                Livro l = new Livro(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
                livros.add(l);
            }
            
            while(reader4.hasNextLine()){
                String data[] =  reader4.nextLine().split(",");
                boolean devolvido = Boolean.parseBoolean(data[1]);
                Cliente cliente = this.getCliente(data[3]);
                String ISBNs[] = data[4].split("/");
                
                // livros emprestados
                List<Livro> listaLivros = new ArrayList<>();
                for(String isbn: ISBNs){
                    Livro livro =  getLivro(isbn);
                    // Se o empréstimo não foi devolvido, marca os livros como emprestados
                    if(!devolvido){
                        livro.setEmprestado(true);
                    }                 
                    listaLivros.add(livro);
                }
                
                // Se o empréstimo não foi devolvido, marca o cliente como empréstimo aberto
                if(!devolvido){
                    cliente.setEmprestimoAberto(true);
                }                
                
                Emprestimo e = new Emprestimo(Integer.parseInt(data[0]), devolvido, this.getFuncionario(data[2]), cliente, listaLivros, data[5]);    
                this.emprestimos.add(e);
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
            try (FileWriter funcs = new FileWriter("data/funcionarios.txt")) {
                for(Funcionario f: funcionarios){
                    funcs.write(f.toString()+"\n");
                }
            }
            
            try (FileWriter cli = new FileWriter("data/clientes.txt")) {
                for(Cliente c: clientes){
                    cli.write(c.toString()+"\n");
                }
            }
            
            try (FileWriter acervo = new FileWriter("data/livros.txt")) {
                for(Livro l: livros){
                    acervo.write(l.toString()+"\n");
                }
            }
            
            try (FileWriter emps = new FileWriter("data/emprestimos.txt")) {
                for(Emprestimo e: this.emprestimos){
                    emps.write(e.toString()+"\n");
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}

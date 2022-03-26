package biblioteca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Banco {
    private List<Emprestimo> historico;
    private List<Livro> livros;
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    
    public Banco(){
        this.historico = new ArrayList<Emprestimo>();
        this.livros = new ArrayList<Livro>();
        this.clientes = new ArrayList<Cliente>();
        this.funcionarios = new ArrayList<Funcionario>();
    }
    
    public boolean possuiLivro(String ISBN){
        boolean possui=false;
        for(Livro l : this.livros){
            if(l.ISBN.equals(ISBN))
                possui=true;
        }
        return possui;
    }
    
    public Livro getLivro(String ISBN){
        for(Livro l : this.livros){
            if(l.ISBN.equals(ISBN))
                return l;
        }
        
        return null;
    }
    
    public boolean adicionaLivro(String titulo, String genero, String autor, String ISBN, int anoPublicacao, int qtdPaginas){
        if(this.possuiLivro(ISBN))
            return false;
        Livro l = new Livro(titulo, autor, genero, ISBN, anoPublicacao, qtdPaginas);
        this.livros.add(l);
        return true;
    }
    
    public boolean removeLivro(String ISBN){
        Livro l = this.getLivro(ISBN);
        if(l==null)
            return false;
        this.livros.remove(l);
        return true;          
    }
    
    public List<Emprestimo> emprestimosEmAberto(){
        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
        for(Emprestimo e : this.historico){
            if(!e.devolvido)
                emprestimos.add(e);
        }
        return emprestimos;
    }
    
    public void cadastraCliente(String nome, String cpf, String endereco, String celular, String dataNascimento){
        Cliente c = new Cliente(nome, cpf, endereco, celular, dataNascimento);
        this.clientes.add(c);
    }
    
    public void cadastraFuncionario(String nome, String cpf, String endereco, String celular, String dataNascimento, String senha, String cargo){
        Funcionario f = new Funcionario(nome, cpf, endereco, celular, dataNascimento, senha, cargo);
        this.funcionarios.add(f);
    }
    
    public void carregaDados(){
        File funcs =  new File("data/funcionarios.txt");
        File cli = new File("data/clientes.txt");
        File livros = new File("data/livros.txt");
        File emprestimos = new File("data/emprestimos.txt");
        
        try {
            Scanner reader = new Scanner(funcs);
            Scanner reader2 = new Scanner(cli);
            Scanner reader3 = new Scanner(livros);
            while(reader.hasNextLine()){
                String data[] = reader.nextLine().split(",");
                Funcionario f = new Funcionario(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                this.funcionarios.add(f);
            }
            
            while(reader2.hasNextLine()){
                String data[] = reader2.nextLine().split(",");
                Cliente c = new Cliente(data[0], data[1], data[2], data[3], data[4], Boolean.parseBoolean(data[5]), Integer.parseInt(data[6]));
                this.clientes.add(c);
            }
            
            while(reader3.hasNextLine()){
                String data[] = reader3.nextLine().split(",");
                Livro l = new Livro(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]), Boolean.parseBoolean(data[6]));
                this.livros.add(l);
            }
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardaDados(){
        try {
            FileWriter funcs =  new FileWriter("data/funcionarios.txt");
            FileWriter cli = new FileWriter("data/clientes.txt");
            FileWriter acervo = new FileWriter("data/livros.txt");
            
            
            for(Funcionario f: this.funcionarios){
                funcs.write(f.toString());
                funcs.close();
            }
            
            for(Cliente c: this.clientes){
                cli.write(c.toString());
                cli.close();
            }
            
            for(Livro l: this.livros){
                acervo.write(l.toString());
                acervo.close();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}

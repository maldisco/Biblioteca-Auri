package biblioteca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class Banco {
    private static List<Emprestimo> historico;
    private static List<Livro> livros;
    private static List<Cliente> clientes;
    private static List<Funcionario> funcionarios;
    
    public Banco(){
        Banco.historico = new ArrayList<>();
        Banco.livros = new ArrayList<>();
        Banco.clientes = new ArrayList<>();
        Banco.funcionarios = new ArrayList<>();
    }
        
    public static Livro getLivro (String ISBN) {
        return livros.stream().filter(livro -> ISBN.equals(livro.getISBN())).findFirst().orElse(null);
    }
    
    public static Cliente getCliente (String CPF) {
        return clientes.stream().filter(cliente -> CPF.equals(cliente.getCPF())).findFirst().orElse(null);
    }
    
    public static Funcionario getFuncionario (String CPF) {
        return funcionarios.stream().filter(func -> CPF.equals(func.getCPF())).findFirst().orElse(null);
    }
    
    public static List<Emprestimo> emprestimosEmAberto(){
        return historico.stream().filter(emprestimo -> emprestimo.devolvido==false).collect(Collectors.toList());
    }
        
    public boolean adicionaLivro(String titulo, String genero, String autor, String ISBN, int anoPublicacao, int qtdPaginas){
        if(Banco.getLivro(ISBN)!=null)
            return false;
        Livro l = new Livro(titulo, autor, genero, ISBN, anoPublicacao, qtdPaginas);
        livros.add(l);
        return true;
    }
    
    public boolean removeLivro(String ISBN){
        Livro l = Banco.getLivro(ISBN);
        if(l==null)
            return false;
        livros.remove(l);
        return true;          
    }
        
    public void cadastraCliente(String nome, String cpf, String endereco, String celular, String dataNascimento){
        Cliente c = new Cliente(nome, cpf, endereco, celular, dataNascimento);
        clientes.add(c);
    }
    
    public void cadastraFuncionario(String nome, String cpf, String endereco, String celular, String dataNascimento, String senha, String cargo){
        Funcionario f = new Funcionario(nome, cpf, endereco, celular, dataNascimento, senha, cargo);
        funcionarios.add(f);
    }
    
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
                Livro l = new Livro(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]), Boolean.parseBoolean(data[6]));
                livros.add(l);
            }
            
            while(reader4.hasNextLine()){
                String data[] =  reader4.nextLine().split(",");
                String livros[] = data[3].split("/");
                List<Livro> l = new ArrayList<Livro>();
                for(String isbn: livros){
                    l.add(Banco.getLivro(isbn));
                }
                
                Emprestimo e = new Emprestimo(Boolean.parseBoolean(data[0]), data[1], data[2], l, Float.parseFloat(data[4]), data[5]);    
                historico.add(e);
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

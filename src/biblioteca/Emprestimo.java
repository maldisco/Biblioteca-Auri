package biblioteca;

import java.time.LocalDate;
import java.util.*;

public class Emprestimo {
    protected boolean devolvido=false;
    protected String cpfFuncionario, cpfCliente;
    protected List<Livro> livros;
    protected float total;
    protected LocalDate data;
    
    public Emprestimo(String cpfFunc, String cpfCli){
        this.cpfFuncionario = cpfFunc;
        this.cpfCliente = cpfCli;
        this.data=LocalDate.now();
        this.livros=new ArrayList<>();
        this.total=0;
    }
    
    public Emprestimo(String cpfFunc, String cpfCli, List<Livro> livros){
        this.cpfFuncionario = cpfFunc;
        this.cpfCliente = cpfCli;
        this.data=LocalDate.now();
        this.livros=livros;
        this.total=0;
    }
    
    public Emprestimo(boolean devolvido, String cpfFunc, String cpfCli, List<Livro> livros, float total, String data){
        this.devolvido = devolvido;
        this.cpfFuncionario = cpfFunc;
        this.cpfCliente = cpfCli;
        this.data = LocalDate.parse(data);
        this.livros = livros;
        this.total = total;
    }
    
    public void adicionarLivro(Livro l){
        if(!this.livros.contains(l)){
            this.livros.add(l);
        }
    }
    
    public void removerLivro(int id){
        this.livros.remove(id);
    }
    
    public void zerar(){
        this.livros.clear();
    }
    
    public void calcularTotal(int dias){
        this.total=1*dias;
    }
    
    public String toString(){
        List<String> listaIsbn = new ArrayList<String>();
        for(Livro l: this.livros){
            listaIsbn.add(l.getISBN());
        }
        String isbn = String.join("/", listaIsbn);
        
        return String.join(",", Boolean.toString(devolvido), cpfFuncionario, cpfCliente, isbn, Float.toString(total), data.toString());
    }
}

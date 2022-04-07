package biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Emprestimo {
    protected boolean devolvido=false;
    protected Funcionario funcionario;
    protected Cliente cliente;
    protected List<Livro> livros;
    protected LocalDate data;
    protected int id;
        
    public Emprestimo(int id, Funcionario func, Cliente cli, List<Livro> livros){
        this.id = id;
        this.funcionario = func;
        this.cliente = cli;
        this.data=LocalDate.now();
        this.livros=livros;
    }
    
    public Emprestimo(int id, boolean devolvido, Funcionario func, Cliente cli, List<Livro> livros, String data){
        this.id = id;
        this.devolvido = devolvido;
        this.funcionario = func;
        this.cliente = cli;
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        this.livros = livros;
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public List<Livro> getLivros() {
        return livros;
    }
    
    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
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
    
    public String toString(){
        List<String> listaIsbn = new ArrayList<String>();
        for(Livro l: this.livros){
            listaIsbn.add(l.getISBN());
        }
        String isbn = String.join("/", listaIsbn);
        
        return String.join(",", Integer.toString(id), Boolean.toString(devolvido), funcionario.getCPF(), cliente.getCPF(), isbn, data.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }
    
    public String[] info(){
        List<String> livros = new ArrayList<String>();
        for(Livro l: this.livros){
            livros.add(l.titulo);
        }
        String nomes = String.join(", ", livros);
        String[] infos = {String.valueOf(id), this.cliente.nome, this.cliente.cpf, this.funcionario.nome, nomes};
        return infos ;
    }
    

}

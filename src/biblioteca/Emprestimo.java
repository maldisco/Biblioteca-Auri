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
        
    @Override
    public String toString(){
        List<String> listaIsbn = new ArrayList<>();
        for(Livro l: this.livros){
            listaIsbn.add(l.getISBN());
        }
        String isbn = String.join("/", listaIsbn);
        
        return String.join(",", Integer.toString(id), Boolean.toString(devolvido), funcionario.getCPF(), cliente.getCPF(), isbn, data.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }
    
    /**
     * Adiciona um livro ao empréstimo
     * @param l 
     */
    public void adicionarLivro(Livro l){
        if(!this.livros.contains(l)){
            this.livros.add(l);
        }
    }
    
    /**
     * Retorna todas as principais informações do empréstimo
     * @return String[]
     */
    public String[] info(){
        List<String> livrosEmprestados = new ArrayList<>();
        for(Livro l: this.livros){
            livrosEmprestados.add(l.titulo);
        }
        String nomes = String.join(", ", livrosEmprestados);
        String[] infos = {String.valueOf(id), this.cliente.nome, this.cliente.cpf, this.funcionario.nome, nomes};
        return infos ;
    }
    

}

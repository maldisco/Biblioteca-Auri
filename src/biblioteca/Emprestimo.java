package biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Emprestimo implements Tabelavel{
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
        this.data = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
        
        return String.join(",", Integer.toString(id), Boolean.toString(devolvido), funcionario.getCPF(), cliente.getCPF(), isbn, data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    
    /**
     * Retorna todas as principais informações do empréstimo
     * @return String[]
     */
    @Override
    public String[] info(){
        List<String> livrosEmprestados = new ArrayList<>();
        for(Livro l: this.livros){
            livrosEmprestados.add(l.titulo);
        }
        String nomes = String.join(", ", livrosEmprestados);
        String emp = (devolvido) ? "Sim" : "Não";
        
        String[] infos = {Integer.toString(id), this.cliente.nome, this.cliente.cpf, this.funcionario.nome, nomes, data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), emp};
        return infos ;
    }
    

}

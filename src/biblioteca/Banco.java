package biblioteca;

import java.time.LocalDate;
import java.util.*;


public class Banco {
    private List<Emprestimo> historico;
    private List<Livro> livros;
    private List<Cliente> clientes;
    
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
    
    public void cadastraCliente(String nome, String cpf, String endereco, String celular, LocalDate dataNascimento){
        Cliente c = new Cliente(nome, cpf, endereco, celular, dataNascimento);
        this.clientes.add(c);
    }
    
    public void cadastraFuncionario(String nome, String cpf, String endereco, String celular, LocalDate dataNascimento, String senha, String cargo){
        Funcionario f = new Funcionario(nome, cpf, endereco, celular, dataNascimento, senha, cargo);
        /* FINALIZAR -> ADICIONAR FUNCIONARIO AO ARQUIVO DE FUNCIONARIOS */
    }
}

package biblioteca;

import java.time.LocalDate;
import java.util.*;

public class Carrinho {
    protected List<Livro> livros;
    protected float total;
    protected LocalDate data;
    
    public Carrinho(){
        this.data=LocalDate.now();
        this.livros=new ArrayList<Livro>();
        this.total=0;
    }
    
    public Carrinho(List<Livro> livros){
        this.data=LocalDate.now();
        this.livros=livros;
        this.total=0;
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
}

class Emprestimo{
    protected boolean devolvido=false;
    private LocalDate dataDevolucao;
    private Carrinho carrinho;
    private Funcionario funcionario;
    private Cliente cliente;
    
    public Emprestimo(int dias, Carrinho c, Funcionario f, Cliente cl){
        this.carrinho=c;
        this.funcionario=f;
        this.cliente=cl;
        this.dataDevolucao=c.data.plusDays(dias);
    }
    
    public void devolver(){
        this.devolvido=true;
    }
}
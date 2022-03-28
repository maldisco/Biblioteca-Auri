package biblioteca;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

public class Pessoa {
    protected String nome, cpf, endereco, celular, dataNascimento;
    
    public Pessoa(String nome, String cpf, String endereco, String celular, String dataNascimento){
        this.nome=nome;
        this.cpf=cpf;
        this.endereco=endereco;
        this.celular=celular;
        this.dataNascimento=dataNascimento;
    }
    
    public String getCPF(){
        return this.cpf;
    }
}

class Funcionario extends Pessoa {
    private String senha, cargo;
    
    public Funcionario(String nome, String cpf, String endereco, String celular, String dataNascimento, String senha, String cargo){
        super(nome, cpf, endereco, celular, dataNascimento);
        this.senha=senha;
        this.cargo=cargo;
    }
    
    @Override
    public String toString(){
        return String.join(",", this.nome, this.cpf, this.endereco, this.celular, this.dataNascimento, this.senha, this.cargo);
    }
}

class Cliente extends Pessoa {
    protected boolean emprestimoAberto=false;
    protected int qtdEmprestimos=0;
    
    public Cliente(String nome, String cpf, String endereco, String celular, String dataNascimento){
        super(nome, cpf, endereco, celular, dataNascimento);
    }
    
    public Cliente(String nome, String cpf, String endereco, String celular, String dataNascimento, boolean emprestimoAberto, int qtdEmprestimos){
        super(nome, cpf, endereco, celular, dataNascimento);
        this.emprestimoAberto=emprestimoAberto;
        this.qtdEmprestimos=qtdEmprestimos;
    }
    
    public String toString(){
        return String.join(",", this.nome, this.cpf, this.endereco, this.celular, this.dataNascimento, Boolean.toString(this.emprestimoAberto),Integer.toString(this.qtdEmprestimos));
    }
}
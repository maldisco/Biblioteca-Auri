package biblioteca;

import java.time.LocalDate;

public class Pessoa {
    protected String nome;
    private String cpf, endereco, celular;
    private LocalDate dataNascimento;
    
    public Pessoa(String nome, String cpf, String endereco, String celular, LocalDate dataNascimento){
        this.nome=nome;
        this.cpf=cpf;
        this.endereco=endereco;
        this.celular=celular;
        this.dataNascimento=dataNascimento;
    }
}

class Funcionario extends Pessoa {
    private String senha, cargo;
    
    public Funcionario(String nome, String cpf, String endereco, String celular, LocalDate dataNascimento, String senha, String cargo){
        super(nome, cpf, endereco, celular, dataNascimento);
        this.senha=senha;
        this.cargo=cargo;
    }
}

class Cliente extends Pessoa {
    protected boolean emprestimoAberto=false;
    protected int qtdEmprestimos;
    
    public Cliente(String nome, String cpf, String endereco, String celular, LocalDate dataNascimento){
        super(nome, cpf, endereco, celular, dataNascimento);
    }
}
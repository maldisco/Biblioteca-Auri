package biblioteca;

/**
 * Superclasse que representa uma pessoa
 */
public class Pessoa {
    protected String nome, cpf, endereco, celular, dataNascimento;
    
    public Pessoa(String nome, String cpf, String endereco, String celular, String dataNascimento){
        this.nome=nome;
        this.cpf=cpf;
        this.endereco=endereco;
        this.celular=celular;
        this.dataNascimento=dataNascimento;
    }
    
    /**
     * Retorna o CPF da pessoa
     * @return String
     */
    public String getCPF(){
        return this.cpf;
    }
}

/**
 * Subclasse da classe Pessoa, representa um funcionário
 */
class Funcionario extends Pessoa {
    private String senha, cargo;
    
    public Funcionario(String nome, String cpf, String endereco, String celular, String dataNascimento, String senha, String cargo){
        super(nome, cpf, endereco, celular, dataNascimento);
        this.senha=senha;
        this.cargo=cargo;
    }
    
    /**
     * Retorna todos os dados do funcionário
     * @return String
     */
    @Override
    public String toString(){
        return String.join(",", this.nome, this.cpf, this.endereco, this.celular, this.dataNascimento, this.senha, this.cargo);
    }
    
    /**
     * Recebe uma senha e checa se é a senha correta para um objeto Funcionario
     * @param senha
     * @return boolean (correta ou incorreta)
     */
    public boolean validar(String senha){
        return this.senha.equals(senha);
    }
    
    /**
     * Checa se o cargo do Funcionario é 'Gerente'
     * @return boolean (é ou não é)
     */
    public boolean ehGerente(){
        return this.cargo.equals("Gerente");
    }
}

/**
 * Subclasse da classe Pessoa, representa um cliente
 */
class Cliente extends Pessoa {
    protected boolean emprestimoAberto=false;
    protected int qtdEmprestimos=0;
    
    /**
     * Construtor padrão
     * @param nome
     * @param cpf
     * @param endereco
     * @param celular
     * @param dataNascimento 
     */
    public Cliente(String nome, String cpf, String endereco, String celular, String dataNascimento){
        super(nome, cpf, endereco, celular, dataNascimento);
    }
    
    /**
     * Construtor com todos os parâmetros (a ser utilizado para carregar clientes do banco)
     * @param nome
     * @param cpf
     * @param endereco
     * @param celular
     * @param dataNascimento
     * @param emprestimoAberto
     * @param qtdEmprestimos 
     */
    public Cliente(String nome, String cpf, String endereco, String celular, String dataNascimento, boolean emprestimoAberto, int qtdEmprestimos){
        super(nome, cpf, endereco, celular, dataNascimento);
        this.emprestimoAberto=emprestimoAberto;
        this.qtdEmprestimos=qtdEmprestimos;
    }
    
    /**
     * Retorna todos os dados do cliente
     * @return String
     */
    @Override
    public String toString(){
        return String.join(",", this.nome, this.cpf, this.endereco, this.celular, this.dataNascimento, Boolean.toString(this.emprestimoAberto),Integer.toString(this.qtdEmprestimos));
    }
}
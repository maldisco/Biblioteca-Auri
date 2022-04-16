package biblioteca;

/**
 * Superclasse que representa uma pessoa.
 * Contém atributos básicos de identificação e contato
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
    
    public String getCPF(){
        return this.cpf;
    }
}

/**
 * Interface que servirá para definir que uma classe pode ser visualizada em uma JTable
 */
interface Tabelavel{
    
    /**
     * Método para exportar as principais informações da classe 
     * @return String[]
     */
    public String[] info();
}

/**
 * Subclasse da classe Pessoa, representa um funcionário
 * Contém os atributos senha (entrar no sistema) e cargo (controlar as funções disponíveis)
 */
class Funcionario extends Pessoa implements Tabelavel{
    private String senha, cargo;
    
    public Funcionario(String nome, String cpf, String endereco, String celular, String dataNascimento, String senha, String cargo){
        super(nome, cpf, endereco, celular, dataNascimento);
        this.senha=senha;
        this.cargo=cargo;
    }

    public String getNome() {
        return nome;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
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
    
    /**
     * Retorna as principais informações do funcionário
     * @return String[]
     */
    @Override
    public String[] info(){
        String[] infos = {nome, cpf, endereco, celular, dataNascimento, cargo, senha};
        return infos;
    }
}

/**
 * Subclasse da classe Pessoa, representa um cliente
 * Contém atributos para controle de empréstimo
 */
class Cliente extends Pessoa implements Tabelavel{
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
    public Cliente(String nome, String cpf, String endereco, String celular, String dataNascimento, int qtdEmprestimos){
        super(nome, cpf, endereco, celular, dataNascimento);
        this.qtdEmprestimos=qtdEmprestimos;
    }

    public int getQtdEmprestimos() {
        return qtdEmprestimos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public void setQtdEmprestimos(int qtdEmprestimos) {
        this.qtdEmprestimos = qtdEmprestimos;
    }
    
    public void setEmprestimoAberto(boolean emprestimoAberto) {
        this.emprestimoAberto = emprestimoAberto;
    }
    
    public boolean getEmprestimoAberto(){
        return this.emprestimoAberto;
    }
    
    @Override
    public String toString(){
        return String.join(",", this.nome, this.cpf, this.endereco, this.celular, this.dataNascimento, Integer.toString(this.qtdEmprestimos));
    }
    
    /**
     * Retorna as principais informações do cliente
     * @return String[]
     */
    @Override
    public String[] info(){
        String empAberto = (emprestimoAberto) ? "Sim" : "Não";
        String[] infos = {nome, cpf, endereco, celular, dataNascimento, Integer.toString(qtdEmprestimos), empAberto};
        return infos;
    }
    
    
}
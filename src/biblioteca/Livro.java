package biblioteca;


public class Livro {
    protected String titulo, autor, ISBN, editora;
    protected int anoPublicacao;
    protected boolean emprestado=false;
    
    public Livro(String titulo, String autor, String editora, String ISBN, int anoPublicacao){
        this.titulo=titulo;
        this.autor=autor;
        this.ISBN=ISBN;
        this.editora = editora;
        this.anoPublicacao=anoPublicacao;
    }
    
    public Livro(String titulo, String autor, String editora, String ISBN, int anoPublicacao, boolean emprestado){
        this.titulo=titulo;
        this.autor=autor;
        this.ISBN=ISBN;
        this.editora=editora;
        this.anoPublicacao=anoPublicacao;
        this.emprestado=emprestado;
    }
    
    @Override
    public String toString(){
        return String.join(",", titulo, autor, editora, ISBN, Integer.toString(anoPublicacao), Boolean.toString(emprestado));
    }
    
    public String getISBN(){
        return this.ISBN;
    }
    
    /**
     * Registra o livro como emprestado ou não
     * @param e 
     */
    public void setEmprestado(boolean e){
        this.emprestado = e;
    }
    
    /**
     * Retorna o estado do livro (emprestado ou não)
     * @return boolean
     */
    public boolean getEmprestado(){
        return this.emprestado;
    }
    
    /**
     * Retorna as principais informações do livro
     * @return String[]
     */
    public String[] info(){
        String[] infos = {titulo, autor, ISBN, editora, Integer.toString(anoPublicacao)};
        return infos;
    }
}

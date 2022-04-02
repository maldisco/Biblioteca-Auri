package biblioteca;


public class Livro {
    protected String titulo, genero, autor, ISBN;
    protected int anoPublicacao;
    protected boolean emprestado=false;
    
    public Livro(String titulo, String genero, String autor, String ISBN, int anoPublicacao){
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
        this.ISBN=ISBN;
        this.anoPublicacao=anoPublicacao;
    }
    
    public Livro(String titulo, String genero, String autor, String ISBN, int anoPublicacao, boolean emprestado){
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
        this.ISBN=ISBN;
        this.anoPublicacao=anoPublicacao;
        this.emprestado=emprestado;
    }
    
    public String toString(){
        return String.join(",", titulo, genero, autor, ISBN, Integer.toString(anoPublicacao), Boolean.toString(emprestado));
    }
    
    public String getISBN(){
        return this.ISBN;
    }
}

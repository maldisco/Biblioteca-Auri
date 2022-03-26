package biblioteca;


public class Livro {
    protected String titulo, genero, autor, ISBN;
    protected int anoPublicacao, qtdPaginas;
    protected boolean emprestado=false;
    
    public Livro(String titulo, String genero, String autor, String ISBN, int anoPublicacao, int qtdPaginas){
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
        this.ISBN=ISBN;
        this.anoPublicacao=anoPublicacao;
        this.qtdPaginas=qtdPaginas;
    }
    
    public Livro(String titulo, String genero, String autor, String ISBN, int anoPublicacao, int qtdPaginas, boolean emprestado){
        this.titulo=titulo;
        this.genero=genero;
        this.autor=autor;
        this.ISBN=ISBN;
        this.anoPublicacao=anoPublicacao;
        this.qtdPaginas=qtdPaginas;
        this.emprestado=emprestado;
    }
    
    public String toString(){
        return String.join(",", titulo, genero, autor, ISBN, Integer.toString(anoPublicacao), Integer.toString(qtdPaginas), Boolean.toString(emprestado));
    }
}

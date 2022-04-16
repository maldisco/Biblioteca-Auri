package biblioteca;


public class Livro implements Tabelavel{
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
    
    @Override
    public String toString(){
        return String.join(",", titulo, autor, editora, ISBN, Integer.toString(anoPublicacao));
    }
    
    public String getISBN(){
        return this.ISBN;
    }
    
    public boolean getEmprestado(){
        return this.emprestado;
    }
    
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public void setEmprestado(boolean e){
        this.emprestado = e;
    }
     
    /**
     * Retorna as principais informações do livro
     * @return String[]
     */
    @Override
    public String[] info(){
        String emp = (emprestado) ? "Sim" : "Não";
        String[] infos = {titulo, autor, ISBN, editora, Integer.toString(anoPublicacao), emp};
        return infos;
    }
}

package model;

public class Categoria {
    //Attributes
    private Long id;
    private String nome;

    //Constructors
    public Categoria() { 
    }

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Categoria(String nome) {
        this.nome = nome;
    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //toString

    @Override 
    public String toString(){
        return "Categoria | id: +" + id + " | Nome: "+ nome;
    }
        
}

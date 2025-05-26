package model;


public class Curso {
        
    private String codigo;
    private String nome;

    public Curso(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return "(ID: " + codigo + " -> Nome: " + nome + ") - ";
    }
}

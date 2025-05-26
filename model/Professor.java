package model;

import java.util.List;


public class Professor {
    private String matricula;
    private String nome;
    private List<Disciplina> disciplinas;
    private Curso curso;

    public Professor(String matricula, String nome, List<Disciplina> disciplinas, Curso curso) {
        this.matricula = matricula;
        this.nome = nome;
        this.disciplinas = disciplinas;
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String toString() {
        return "Professor{" +
               "matricula='" + matricula + '\'' +
               ", nome='" + nome + '\'' +
               ", disciplinas=" + disciplinas +
               ", curso=" + curso +
               '}';
    }
}

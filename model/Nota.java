package model;

public class Nota {
    private Aluno aluno;
    private Disciplina disciplina;
    private double valor;
    private int trimestre;

    public Nota(Aluno aluno, Disciplina disciplina, double valor, int trimestre) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.valor = valor;
        this.trimestre = trimestre;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
    this.valor = valor;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public String toString() {
        return "Aluno: " + aluno.getNome() +
               " | Disciplina: " + disciplina.getNome() +
               " | Nota: " + valor +
               " | Trimestre: " + trimestre;
    }
}

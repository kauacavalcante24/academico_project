import java.util.*;
import model.*;
import view.View;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Map<String, Curso> cursos = new HashMap<>();
        Map<String, Professor> professores = new HashMap<>();
        Map<String, Disciplina> disciplinas = new HashMap<>();
        Map<String, Aluno> alunos = new HashMap<>();
        List<Nota> notas = new ArrayList<>();

        System.out.println();
        System.out.println("=========== PROJETO ACADÊMICO ===========");
        System.out.println();
        
        System.out.println("Cursos ------------------");
        System.out.println();
        System.out.print("Quantos cursos deseja cadastrar? (digitar o número): ");
        String curso_input = input.nextLine();
        int qtdCursos = Integer.parseInt(curso_input);
        View.cadastroCurso(cursos, qtdCursos);

        System.out.println("Disciplinas ------------------");
        System.out.println();       
        System.out.print("Quantas disciplinas deseja cadastrar? (digitar o número): ");
        String disciplina_input = input.nextLine();
        int qtdDisciplinas = Integer.parseInt(disciplina_input);
        View.cadastroDisciplina(disciplinas, qtdDisciplinas);

        System.out.println("Professores ------------------");
        System.out.println();
        System.out.print("Quantos professores deseja cadastrar? (digitar o número) ");
        int qtdProfessores = Integer.parseInt(input.nextLine());
        View.cadastroProfessor(cursos, professores, disciplinas, qtdProfessores);

        System.out.println("Alunos ------------------");
        System.out.println();        
        System.out.print("Quantos alunos deseja cadastrar? (digitar o número) ");
        int qtdAlunos = Integer.parseInt(input.nextLine());
        View.cadastrarAluno(cursos, alunos, qtdAlunos);

        System.out.println("Notas ------------------");
        System.out.println();
        System.out.print("Quantas notas deseja cadastrar? ");
        int qtdNotas = Integer.parseInt(input.nextLine());
        View.cadastrarNota(alunos, disciplinas, notas, qtdNotas);

        View.mostrarRelatorios(alunos, notas);
        View.mostrarRelatorioGeral(alunos, professores, cursos, disciplinas);

        View.relatorioAlunosPorCursoEDisciplina(cursos, alunos, disciplinas, notas);

        View.relatorioNotasPorAlunoPorCurso(cursos, alunos, notas);

        View.relatorioConclusaoCurso(alunos, notas);
        
        for (Aluno aluno : alunos.values()) {
            if (View.checarConclusaoCurso(aluno, notas)) {
                View.emitirCertificado(aluno);
            }
        }
        input.close();
    }
}
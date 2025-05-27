import java.util.*;
import model.*;
import view.View;
import service.*;


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

        Relatorios.mostrarRelatorios(alunos, notas);
        Relatorios.mostrarRelatorioGeral(alunos, professores, cursos, disciplinas);
        Relatorios.relatorioAlunosPorCursoEDisciplina(cursos, alunos, disciplinas, notas);
        Relatorios.relatorioNotasPorAlunoPorCurso(cursos, alunos, notas);
        Relatorios.relatorioConclusaoCurso(alunos, notas);
        
        for (Aluno aluno : alunos.values()) {
            if (Conclusao.checarConclusaoCurso(aluno, notas)) {
                Conclusao.emitirCertificado(aluno);
            }
        }
        input.close();
    }
}
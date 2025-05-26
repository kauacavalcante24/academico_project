package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Aluno;
import model.Curso;
import model.Disciplina;
import model.Nota;
import model.Professor;

public class View {
    private static Scanner input = new Scanner(System.in);

    public static void cadastrarCurso(Map<String, Curso> cursos, int qtdCursos) {

        for (int i = 0; i < qtdCursos; i++) {
            System.out.print("Código do curso: ");
            String codigoCurso = input.nextLine();

            System.out.print("Nome do curso: ");
            String nomeCurso = input.nextLine();

            Curso curso = new Curso(codigoCurso, nomeCurso);

            if (cursos.containsKey(codigoCurso)) {
                System.out.println("Já existe um curso com esse código. Tente novamente.");
                i--;
                continue;
            }

            cursos.put(codigoCurso, curso);
            System.out.println("Curso cadastrado com sucesso!\n");
        }
    }

    public static void cadastrarDisciplina(Map<String, Disciplina> disciplinas, int qtdDisciplinas) {

        for (int i = 0; i < qtdDisciplinas; i++) {
            System.out.print("Código da disciplina: ");
            String codigoDisciplina  = input.nextLine();
            System.out.print("Nome da disciplina: ");
            String nomeDisciplina = input.nextLine();

            Disciplina disciplina = new Disciplina(codigoDisciplina, nomeDisciplina);
            if (disciplinas.containsKey(codigoDisciplina)) {
                System.out.println("Já existe uma disciplina com esse código. Tente novamente.");
                i--;
                continue;
            }
            disciplinas.put(codigoDisciplina, disciplina);
            System.out.println("Disciplina cadastrada com sucesso!\n");
        }
    }

    public static void cadastrarProfessor(Map<String, Curso> cursos, Map<String, Professor> professores, Map<String, Disciplina> disciplinas, int qtdProfessores) {

        for (int i = 0; i < qtdProfessores; i++) {
            System.out.print("Matrícula do professor: ");
            String matriculaProf = input.nextLine();

            System.out.print("Nome do professor: ");
            String nomeProf = input.nextLine();

            System.out.print("Código do curso que o professor leciona: ");
            String codCursoProf = input.nextLine();
            Curso cursoProf = cursos.get(codCursoProf);

            if (cursoProf == null) {
                System.out.println("Curso não encontrado. Tente novamente.");
                i--;
                continue;
            }
            List<Disciplina> disciplinasDoProfessor = new ArrayList<>();
            System.out.print("Quantas disciplinas esse professor leciona? ");
            int qtdDisciplinasProfessor = Integer.parseInt(input.nextLine());

            for (int j = 0; j < qtdDisciplinasProfessor; j++) {
                System.out.print("Código da disciplina " + (j + 1) + ": ");
                String codigoDisciplina = input.nextLine();
                Disciplina disciplina = disciplinas.get(codigoDisciplina);

                if (disciplina == null) {
                    System.out.println("Disciplina não encontrada! Tente novamente.");
                    j--;
                    continue;
                }

                disciplinasDoProfessor.add(disciplina);
            }
            Professor prof = new Professor(matriculaProf, nomeProf, disciplinasDoProfessor, cursoProf);
            professores.put(matriculaProf, prof);
            System.out.println("Professor cadastrado com sucesso!\n");
            System.out.println();
        }
    }

    public static void cadastrarAluno(Map<String, Curso> cursos, Map<String, Aluno> alunos, int qtdAlunos) {
        for (int i = 0; i < qtdAlunos; i++) {
            System.out.print("Matrícula do aluno: ");
            String matricula = input.nextLine();

            System.out.print("Nome do aluno: ");
            String nomeAluno = input.nextLine();

            System.out.print("Código do curso do aluno: ");
            String codCursoAluno = input.nextLine();

            Curso cursoAluno = cursos.get(codCursoAluno);
            if (cursoAluno == null) {
                System.out.println("Curso não encontrado. Tente novamente.");
                i--; 
                continue;
            }
            Aluno a = new Aluno(matricula, nomeAluno, cursoAluno);
            alunos.put(matricula, a);
            System.out.println();
        }
    }

    public static void cadastrarNota(Map<String, Aluno> alunos, Map<String, Disciplina> disciplinas, List<Nota> notas, int qtdNotas) {
        for (int i = 0; i < qtdNotas; i++) {
            System.out.print("Matrícula do aluno para a nota: ");
            String matriculaAluno = input.nextLine();

            Aluno aluno = alunos.get(matriculaAluno);
            if (aluno == null) {
                System.out.println("Aluno não encontrado. Tente novamente.");
                i--;
                continue;
            }
            System.out.print("Código da disciplina: ");
            String codigoDisciplina = input.nextLine();
            Disciplina disciplina = disciplinas.get(codigoDisciplina);
            if (disciplina == null) {
                System.out.println("Disciplina não encontrada. Tente novamente.");
                i--;
                continue;
            }
            System.out.print("Trimestre (1, 2 ou 3): ");
            int trimestre;
            try {
                trimestre = Integer.parseInt(input.nextLine());
                if (trimestre < 1 || trimestre > 3) {
                    throw new NumberFormatException(); 
                }
            } catch (NumberFormatException e) {
                System.out.println("Trimestre inválido. Tente novamente.");
                i--;
                continue;
            }
            boolean jaTemNota = false;
            for (Nota n : notas) {
                if (n.getAluno().getMatricula().equals(aluno.getMatricula()) &&
                    n.getDisciplina().getCodigo().equals(disciplina.getCodigo()) &&
                    n.getTrimestre() == trimestre) {
                    jaTemNota = true;
                    break;
                }
            }
            if (jaTemNota) {
                System.out.println("Este aluno já possui uma nota para esta disciplina neste trimestre.");
                i--;
                continue;
            }
            System.out.print("Nota (ex: 7.5): ");
            double valorNota;
            try {
                valorNota = Double.parseDouble(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Nota inválida. Tente novamente.");
                i--;
                continue;
            }

            Nota nota = new Nota(aluno, disciplina, valorNota, trimestre);
            notas.add(nota);
            System.out.println();
        }
    }

    public static void mostrarRelatorios(Map<String, Aluno> alunos, List<Nota> notas) {
        System.out.println("RELATÓRIOS ------------------");
        System.out.println();
        System.out.println("Alunos cadastrados");
        for (Aluno a : alunos.values()) {
            System.out.println(a);
        } 
        System.out.println("\nNotas cadastradas");
        for (Nota n : notas) {
            System.out.println(n);
        }
    }
}

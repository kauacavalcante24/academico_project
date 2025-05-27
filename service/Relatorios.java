package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.*;


public class Relatorios {
    public static Scanner input = new Scanner(System.in);

    public static void mostrarRelatorios(Map<String, Aluno> alunos, List<Nota> notas) {
        System.out.println("RELATÓRIOS ------------------");
        System.out.println();
        
        System.out.println("Alunos cadastrados:");
        for (Aluno a : alunos.values()) {
            System.out.println(a);
        } 

        System.out.println("\nNotas cadastradas:");
        for (Nota n : notas) {
            System.out.println(n);
        }

        for (Aluno aluno : alunos.values()) {
            double soma = 0;
            int count = 0;
            List<Nota> notasDoAluno = new ArrayList<>();

            // Coletar notas do aluno e somar os valores
            for (Nota nota : notas) {
                if (nota.getAluno().equals(aluno)) {
                    notasDoAluno.add(nota);
                    soma += nota.getValor();
                    count++;
                }
            }

            // Calcular média
            double media = (count > 0) ? soma / count : 0;

            // Determinar status
            String status;
            if (media >= 7.0) {
                status = "Aprovado";
            } else if (media >= 4.0) {
                status = "Em Recuperação";
            } else {
                status = "Reprovado";
            }

            // Exibir situação
            System.out.println("\nAluno: " + aluno.getNome() +
                            " | Média: " + String.format("%.2f", media) +
                            " | Status: " + status);

            // Se estiver reprovado (média < 4), exibir mensagem
            if (media < 4.0) {
                System.out.println("Aluno reprovado no curso.");
            }

            // Se estiver em recuperação, exibir disciplinas com nota < 7
            if (status.equals("Em Recuperação")) {
                System.out.println("Disciplinas com nota abaixo de 7:");
                for (Nota nota : notasDoAluno) {
                    if (nota.getValor() < 7.0) {
                        System.out.println("- " + nota.getDisciplina().getNome() + ": " + nota.getValor());
                    }
                }

                // Permitir ao usuário alterar notas
                System.out.print("Deseja alterar alguma dessas notas? (s/n): ");
                String resposta = input.nextLine();
                if (resposta.equalsIgnoreCase("s")) {
                    for (Nota nota : notasDoAluno) {
                        if (nota.getValor() < 7.0) {
                            System.out.print("Nova nota para a disciplina " +
                                            nota.getDisciplina().getNome() + ": ");
                            try {
                                double novaNota = Double.parseDouble(input.nextLine());
                                nota.setValor(novaNota);
                            } catch (NumberFormatException e) {
                                System.out.println("Valor inválido. A nota não foi alterada.");
                            }
                        }
                    }
                }
            }
            System.out.print("\nDeseja alterar alguma dessas notas? (s/n): ");
            String resposta = input.nextLine();
            if (resposta.equalsIgnoreCase("s")) {
                for (Nota nota : notasDoAluno) {
                    if (nota.getValor() < 7.0) {
                        System.out.print("Nova nota para a disciplina " +
                                        nota.getDisciplina().getNome() + ": ");
                        try {
                            double novaNota = Double.parseDouble(input.nextLine());
                            nota.setValor(novaNota);
                        } catch (NumberFormatException e) {
                            System.out.println("Valor inválido. A nota não foi alterada.");
                        }
                    }
                }
            }
        }
    }

    public static void mostrarRelatorioGeral(Map<String, Aluno> alunos,
                                            Map<String, Professor> professores,
                                            Map<String, Curso> cursos,
                                            Map<String, Disciplina> disciplinas) {
        System.out.println("\n========== RELATÓRIO GERAL ==========");

        System.out.println("\n--- Alunos Matriculados ---");
        for (Aluno a : alunos.values()) {
            System.out.print("\nMatrícula: " + a.getMatricula() + " | " + "Nome: " + a.getNome());
            System.out.println(" | Curso: " + a.getCurso());
        }

        System.out.println("\n--- Professores Cadastrados ---");
        for (Professor p : professores.values()) {
            System.out.print("\nMatrícula: " + p.getMatricula() + " | " + "Nome: " + p.getNome());
            System.out.println(" | " + "Disciplina(as): " + p.getDisciplinas() + " | Curso: " + p.getCurso());
        }

        System.out.println("\n--- Cursos Cadastrados ---");
        for (Curso c : cursos.values()) {
            System.out.println("\nCódigo: " + c.getCodigo() + " | " + "Nome: " + c.getNome());
        }

        System.out.println("\n--- Disciplinas Cadastradas ---");
        for (Disciplina d : disciplinas.values()) {
            System.out.println("\nCódigo: " + d.getCodigo() + " | " + "Nome: " + d.getNome());
        }
    }

    public static void relatorioAlunosPorCursoEDisciplina(Map<String, Curso> cursos, 
                                                    Map<String, Aluno> alunos, 
                                                    Map<String, Disciplina> disciplinas, 
                                                    List<Nota> notas) {
        System.out.println("\n===== RELATÓRIO: Alunos por Curso e Disciplina =====");

        for (Curso curso : cursos.values()) {
            System.out.println("\nCurso: " + curso.getNome() + " (" + curso.getCodigo() + ")");

            // Alunos do curso
            System.out.println("  Alunos matriculados:");
            boolean temAluno = false;
            for (Aluno aluno : alunos.values()) {
                if (aluno.getCurso().equals(curso)) {
                    System.out.println("   - Matrícula: " + aluno.getMatricula() + " | Nome: " + aluno.getNome());
                    temAluno = true;
                }
            }
            if (!temAluno) {
                System.out.println("  Nenhum aluno matriculado neste curso.");
            }

            // Para cada disciplina, mostrar alunos que têm nota nela
            System.out.println("\n  Alunos por disciplina");
            for (Disciplina disciplina : disciplinas.values()) {
                System.out.println("     Disciplina: " + disciplina.getNome() + " (" + disciplina.getCodigo() + ")");

                // Buscar alunos deste curso que têm nota nesta disciplina
                List<Aluno> alunosNaDisciplina = new ArrayList<>();
                for (Nota nota : notas) {
                    if (nota.getDisciplina().equals(disciplina) && 
                        nota.getAluno().getCurso().equals(curso) &&
                        !alunosNaDisciplina.contains(nota.getAluno())) {
                        alunosNaDisciplina.add(nota.getAluno());
                    }
                }
                if (alunosNaDisciplina.isEmpty()) {
                    System.out.println("  Nenhum aluno matriculado nesta disciplina.");
                } else {
                    for (Aluno a : alunosNaDisciplina) {
                        System.out.println("     - Matrícula: " + a.getMatricula() + " | Nome " + a.getNome());
                        }
                    }
            }
        }
    }

    public static void relatorioNotasPorAlunoPorCurso(Map<String, Curso> cursos,
                                                Map<String, Aluno> alunos,
                                                List<Nota> notas) {
        System.out.println("\n===== RELATÓRIO: Notas de Aluno(os) por Curso =====");
    
        for (Curso curso : cursos.values()) {
            System.out.println("\nCurso: " + curso.getNome() + " (" + curso.getCodigo() + ")");
            
            boolean temAluno = false;
            for (Aluno aluno : alunos.values()) {
                if (aluno.getCurso().equals(curso)) {
                    temAluno = true;
                    System.out.println("\nAluno: " + aluno.getNome() + " | Curso: " + curso.getNome());
                    
                    // Listar todas as notas do aluno
                    boolean temNota = false;
                    for (Nota nota : notas) {
                        if (nota.getAluno().equals(aluno)) {
                            temNota = true;
                            System.out.println("Disciplina: " + nota.getDisciplina().getNome() + 
                                            " | Nota: " + nota.getValor() + 
                                            " | Trimestre: " + nota.getTrimestre());
                        }
                    }
                    if (!temNota) {
                        System.out.println("Nenhuma nota cadastrada para este aluno.");
                    }
                }
            }
            if (!temAluno) {
                System.out.println("Nenhum aluno matriculado neste curso.");
            }
        }
    }

    public static void relatorioConclusaoCurso(Map<String, Aluno> alunos, List<Nota> notas) {
        System.out.println("\n===== RELATÓRIO DE CONCLUSÃO DE CURSO =====");
        System.out.println();

        for (Aluno aluno : alunos.values()) {
            // Map para armazenar soma das notas por disciplina
            Map<String, List<Double>> notasPorDisciplina = new HashMap<>();

            // Coletar notas por disciplina para o aluno
            for (Nota nota : notas) {
                if (nota.getAluno().equals(aluno)) {
                    String codigoDisc = nota.getDisciplina().getCodigo();
                    notasPorDisciplina.putIfAbsent(codigoDisc, new ArrayList<>());
                    notasPorDisciplina.get(codigoDisc).add(nota.getValor());
                }
            }

            int disciplinasAprovadas = 0;

            // Calcular média por disciplina e contar aprovadas
            for (Map.Entry<String, List<Double>> entry : notasPorDisciplina.entrySet()) {
                List<Double> notasDisc = entry.getValue();
                double soma = 0;
                for (Double v : notasDisc) {
                    soma += v;
                }
                double mediaDisciplina = soma / notasDisc.size();
                if (mediaDisciplina >= 7.0) {
                    disciplinasAprovadas++;
                }
            }

            // Verificar se concluiu o curso (>= 10 disciplinas aprovadas)
            boolean concluiu = disciplinasAprovadas >= 10;

            System.out.println("Aluno: " + aluno.getNome() +
                            " | Curso: " + aluno.getCurso().getNome() +
                            " | Disciplinas aprovadas: " + disciplinasAprovadas +
                            " | Concluiu curso? " + (concluiu ? "Sim" : "Não"));
            }
    }
}

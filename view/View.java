package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.*;

public class View {
    private static Scanner input = new Scanner(System.in);

    public static void cadastroCurso(Map<String, Curso> cursos, int qtdCursos) {

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

    public static void cadastroDisciplina(Map<String, Disciplina> disciplinas, int qtdDisciplinas) {

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

    public static void cadastroProfessor(Map<String, Curso> cursos, Map<String, Professor> professores, Map<String, Disciplina> disciplinas, int qtdProfessores) {

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
    }   
    public static void mostrarRelatorioGeral(Map<String, Aluno> alunos,
                                            Map<String, Professor> professores,
                                            Map<String, Curso> cursos,
                                            Map<String, Disciplina> disciplinas) {
        System.out.println("\n========== RELATÓRIO GERAL ==========");

        System.out.println("\n--- Alunos Matriculados ---");
        for (Aluno a : alunos.values()) {
            System.out.println(a);
        }

        System.out.println("\n--- Professores Cadastrados ---");
        for (Professor p : professores.values()) {
            System.out.println(p);
        }

        System.out.println("\n--- Cursos Cadastrados ---");
        for (Curso c : cursos.values()) {
            System.out.println(c);
        }

        System.out.println("\n--- Disciplinas Cadastradas ---");
        for (Disciplina d : disciplinas.values()) {
            System.out.println(d);
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
            System.out.println("Alunos matriculados:");
            boolean temAluno = false;
            for (Aluno aluno : alunos.values()) {
                if (aluno.getCurso().equals(curso)) {
                    System.out.println(" - " + aluno.getNome() + " (Matrícula: " + aluno.getMatricula() + ")");
                    temAluno = true;
                }
            }
            if (!temAluno) {
                System.out.println("  Nenhum aluno matriculado neste curso.");
            }

        // Para cada disciplina, mostrar alunos que têm nota nela
            System.out.println("\nAlunos por disciplina:");
            for (Disciplina disciplina : disciplinas.values()) {
                System.out.println("Disciplina: " + disciplina.getNome() + " (" + disciplina.getCodigo() + ")");

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
                        System.out.println("  - " + a.getNome() + " (Matrícula: " + a.getMatricula() + ")");
                        }
                    }
            }
        }
    }
    public static void relatorioNotasPorAlunoPorCurso(Map<String, Curso> cursos,
                                                 Map<String, Aluno> alunos,
                                                 List<Nota> notas) {
        System.out.println("\n===== RELATÓRIO: Notas por Aluno por Curso =====");
    
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
    public static void emitirCertificado(Aluno aluno) {
        if (aluno == null) {
            System.out.println("Aluno inválido.");
            return;
        }

        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n========== CERTIFICADO DE CONCLUSÃO ==========");
        System.out.println("Aluno: " + aluno.getNome());
        System.out.println("Curso: " + aluno.getCurso().getNome());
        System.out.println("Data de emissão: " + dataAtual.format(formatter));
        System.out.println("Parabéns pela conclusão do curso!\n");
    }

    public static boolean checarConclusaoCurso(Aluno aluno, List<Nota> notas) {
        // Mapa para armazenar as notas por disciplina para esse aluno
        Map<String, List<Double>> notasPorDisciplina = new HashMap<>();

        for (Nota nota : notas) {
            if (nota.getAluno().equals(aluno)) {
                String codigoDisciplina = nota.getDisciplina().getCodigo();
                notasPorDisciplina.putIfAbsent(codigoDisciplina, new ArrayList<>());
                notasPorDisciplina.get(codigoDisciplina).add(nota.getValor());
            }
        }

        int disciplinasAprovadas = 0;

        for (List<Double> listaNotas : notasPorDisciplina.values()) {
            double soma = 0;
            for (Double valor : listaNotas) {
                soma += valor;
            }
            double mediaDisciplina = soma / listaNotas.size();

            if (mediaDisciplina >= 7.0) {
                disciplinasAprovadas++;
            }
        }
        return disciplinasAprovadas >= 10;  // pelo menos 10 disciplinas aprovadas
    }
}
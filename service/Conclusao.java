package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Aluno;
import model.Nota;


public class Conclusao {
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
}

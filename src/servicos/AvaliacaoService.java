package servicos;

import modelos.Aluno;
import modelos.Avaliacao;
import modelos.Turma;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AvaliacaoService {
    private final List<Avaliacao> avaliacoes = new ArrayList<>();
    private final AlunoService alunoService;
    private final TurmaService turmaService;

    public AvaliacaoService(AlunoService alunoService, TurmaService turmaService) {
        this.alunoService = alunoService;
        this.turmaService = turmaService;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        // Evita duplicatas para o mesmo aluno na mesma turma
        if (buscarAvaliacao(avaliacao.getAluno().getMatricula(), avaliacao.getTurma().getDisciplina().getCodigo(), avaliacao.getTurma().getSemestre()) == null) {
            avaliacoes.add(avaliacao);
            System.out.println("Avaliação adicionada/atualizada para " + avaliacao.getAluno().getNome() + " na turma de " + avaliacao.getTurma().getDisciplina().getNome());
        } else {
            System.out.println("Avaliação para " + avaliacao.getAluno().getNome() + " na turma de " + avaliacao.getTurma().getDisciplina().getNome() + " já existe. Use 'atualizarNotas' ou 'atualizarPresencas' se quiser modificar.");
        }
    }

    public Avaliacao buscarAvaliacao(String matriculaAluno, String codigoDisciplina, String semestreTurma) {
        return avaliacoes.stream()
                .filter(a -> a.getAluno().getMatricula().equalsIgnoreCase(matriculaAluno) &&
                        a.getTurma().getDisciplina().getCodigo().equalsIgnoreCase(codigoDisciplina) &&
                        a.getTurma().getSemestre().equalsIgnoreCase(semestreTurma))
                .findFirst()
                .orElse(null);
    }

    public List<Avaliacao> listarTodas() {
        return new ArrayList<>(avaliacoes);
    }

    public List<Avaliacao> listarPorTurma(String codigoDisciplina, String semestreTurma) {
        return avaliacoes.stream()
                .filter(a -> a.getTurma().getDisciplina().getCodigo().equalsIgnoreCase(codigoDisciplina) &&
                        a.getTurma().getSemestre().equalsIgnoreCase(semestreTurma))
                .collect(Collectors.toList());
    }

    public List<Avaliacao> listarPorDisciplina(String codigoDisciplina) {
        return avaliacoes.stream()
                .filter(a -> a.getTurma().getDisciplina().getCodigo().equalsIgnoreCase(codigoDisciplina))
                .collect(Collectors.toList());
    }

    public List<Avaliacao> listarPorProfessor(String nomeProfessor) {
        return avaliacoes.stream()
                .filter(a -> a.getTurma().getProfessor().getNome().equalsIgnoreCase(nomeProfessor))
                .collect(Collectors.toList());
    }

    public List<Avaliacao> listarBoletimAluno(String matriculaAluno) {
        return avaliacoes.stream()
                .filter(a -> a.getAluno().getMatricula().equalsIgnoreCase(matriculaAluno))
                .collect(Collectors.toList());
    }

    // Métodos para persistência (salvar/carregar)
    public void salvar(String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (Avaliacao a : avaliacoes) {
                writer.write(a.getAluno().getMatricula() + ";" +
                        a.getTurma().getDisciplina().getCodigo() + ";" +
                        a.getTurma().getSemestre() + ";" +
                        a.getP1() + ";" +
                        a.getP2() + ";" +
                        a.getP3() + ";" +
                        a.getListas() + ";" +
                        a.getSeminario() + ";" +
                        a.getTotalAulas() + ";" +
                        a.getPresencas());
                writer.newLine();
            }
            System.out.println("Avaliações salvas em: " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao salvar avaliações: " + e.getMessage());
        }
    }

    public void inicializar() {
        carregar("avaliacoes.txt");
    }

    public void carregar(String caminho) {
        int novas = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 10) {
                    String matriculaAluno = partes[0];
                    String codigoDisciplina = partes[1];
                    String semestreTurma = partes[2];
                    double p1 = Double.parseDouble(partes[3]);
                    double p2 = Double.parseDouble(partes[4]);
                    double p3 = Double.parseDouble(partes[5]);
                    double listas = Double.parseDouble(partes[6]);
                    double seminario = Double.parseDouble(partes[7]);
                    int totalAulas = Integer.parseInt(partes[8]);
                    int presencas = Integer.parseInt(partes[9]);

                    Aluno aluno = alunoService.buscarPorMatricula(matriculaAluno);
                    Turma turma = turmaService.buscarTurma(codigoDisciplina, semestreTurma);

                    if (aluno != null && turma != null) {
                        Avaliacao avaliacaoExistente = buscarAvaliacao(matriculaAluno, codigoDisciplina, semestreTurma);
                        if (avaliacaoExistente == null) {
                            Avaliacao novaAvaliacao = new Avaliacao(aluno, turma, totalAulas);
                            novaAvaliacao.setP1(p1);
                            novaAvaliacao.setP2(p2);
                            novaAvaliacao.setP3(p3);
                            novaAvaliacao.setListas(listas);
                            novaAvaliacao.setSeminario(seminario);
                            novaAvaliacao.setPresencas(presencas);
                            avaliacoes.add(novaAvaliacao);
                            novas++;
                        }
                    }
                }
            }
            System.out.println("Avaliações carregadas: " + novas + " novas adicionadas.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar avaliações: " + e.getMessage());
        }
    }
}
import java.util.List;
import java.util.Scanner;

public class AvaliacaoUI {
    private final Scanner scanner;
    private final AvaliacaoService avaliacaoService;
    private final TurmaService turmaService;
    private final AlunoService alunoService;
    private final ProfessorService professorService;

    public AvaliacaoUI(Scanner scanner, AvaliacaoService avaliacaoService, TurmaService turmaService, AlunoService alunoService, ProfessorService professorService) {
        this.scanner = scanner;
        this.avaliacaoService = avaliacaoService;
        this.turmaService = turmaService;
        this.alunoService = alunoService;
        this.professorService = professorService;
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n--- MODO AVALIAÇÃO/FREQUÊNCIA ---");
            System.out.println("1 - Lançar Notas e Presença");
            System.out.println("2 - Calcular e Exibir Média/Frequência de um Aluno em Turma");
            System.out.println("3 - Exibir Relatórios");
            System.out.println("4 - Exibir Boletim por Aluno");
            System.out.println("5 - Salvar Avaliações");
            System.out.println("6 - Carregar Avaliações");
            System.out.println("0 - Voltar");
            System.out.print(">> ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> lancarNotasEPresenca();
                case 2 -> calcularEMostrarAvaliacaoIndividual();
                case 3 -> menuRelatorios();
                case 4 -> exibirBoletimAluno();
                case 5 -> avaliacaoService.salvar("avaliacoes.txt");
                case 6 -> avaliacaoService.inicializar();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void lancarNotasEPresenca() {
        System.out.print("Matrícula do aluno: ");
        String matriculaAluno = scanner.nextLine();
        Aluno aluno = alunoService.buscarPorMatricula(matriculaAluno);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.print("Código da disciplina da turma: ");
        String codigoDisciplina = scanner.nextLine();

        System.out.print("Semestre da turma (ex: 2024.1): ");
        String semestreTurma = scanner.nextLine();

        Turma turma = turmaService.buscarTurma(codigoDisciplina, semestreTurma);
        if (turma == null) {
            System.out.println("Turma não encontrada ou aluno não matriculado nesta turma.");
            return;
        }


        if (!turma.getAlunosMatriculados().contains(aluno)) {
            System.out.println("O aluno não está matriculado nesta turma.");
            return;
        }


        Avaliacao avaliacao = avaliacaoService.buscarAvaliacao(matriculaAluno, codigoDisciplina, semestreTurma);
        if (avaliacao == null) {
            // Se não existe, cria uma nova avaliação
            System.out.print("Total de aulas para esta turma: ");
            int totalAulas = scanner.nextInt();
            scanner.nextLine();
            avaliacao = new Avaliacao(aluno, turma, totalAulas);
            avaliacaoService.adicionarAvaliacao(avaliacao); // Adiciona a nova avaliação ao serviço
            System.out.println("Nova avaliação criada para o aluno na turma.");
        } else {
            System.out.println("Avaliação existente encontrada para o aluno nesta turma.");
        }

        System.out.println("Lançar notas (digite -1 para manter a nota atual se já lançada):");
        System.out.print("P1 (" + (avaliacao.getP1() == -1 ? "N/A" : avaliacao.getP1()) + "): ");
        double p1 = scanner.nextDouble();
        if (p1 != -1) avaliacao.setP1(p1);

        System.out.print("P2 (" + (avaliacao.getP2() == -1 ? "N/A" : avaliacao.getP2()) + "): ");
        double p2 = scanner.nextDouble();
        if (p2 != -1) avaliacao.setP2(p2);

        System.out.print("P3 (" + (avaliacao.getP3() == -1 ? "N/A" : avaliacao.getP3()) + "): ");
        double p3 = scanner.nextDouble();
        if (p3 != -1) avaliacao.setP3(p3);

        System.out.print("Listas (L) (" + (avaliacao.getListas() == -1 ? "N/A" : avaliacao.getListas()) + "): ");
        double listas = scanner.nextDouble();
        if (listas != -1) avaliacao.setListas(listas);

        System.out.print("Seminário (S) (" + (avaliacao.getSeminario() == -1 ? "N/A" : avaliacao.getSeminario()) + "): ");
        double seminario = scanner.nextDouble();
        if (seminario != -1) avaliacao.setSeminario(seminario);
        scanner.nextLine();

        System.out.print("Lançar presenças (presença atual: " + avaliacao.getPresencas() + "/" + avaliacao.getTotalAulas() + "): ");
        System.out.print("Número de presenças: ");
        int presencas = scanner.nextInt();
        if (presencas >= 0 && presencas <= avaliacao.getTotalAulas()) {
            avaliacao.setPresencas(presencas);
        } else {
            System.out.println("Número de presenças inválido. Mantendo o valor anterior.");
        }
        scanner.nextLine();

        System.out.println("Notas e presença lançadas/atualizadas com sucesso!");
        System.out.println(avaliacao);
    }

    private void calcularEMostrarAvaliacaoIndividual() {
        System.out.print("Matrícula do aluno: ");
        String matriculaAluno = scanner.nextLine();

        System.out.print("Código da disciplina da turma: ");
        String codigoDisciplina = scanner.nextLine();

        System.out.print("Semestre da turma (ex: 2024.1): ");
        String semestreTurma = scanner.nextLine();

        Avaliacao avaliacao = avaliacaoService.buscarAvaliacao(matriculaAluno, codigoDisciplina, semestreTurma);
        if (avaliacao == null) {
            System.out.println("Avaliação não encontrada para o aluno e turma especificados.");
            return;
        }

        System.out.println("\n--- DETALHES DA AVALIAÇÃO ---");
        System.out.println(avaliacao);
    }

    private void menuRelatorios() {
        int opcao;
        do {
            System.out.println("\n--- RELATÓRIOS ---");
            System.out.println("1 - Relatório por Turma");
            System.out.println("2 - Relatório por Disciplina");
            System.out.println("3 - Relatório por Professor");
            System.out.println("0 - Voltar");
            System.out.print(">> ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> relatorioPorTurma();
                case 2 -> relatorioPorDisciplina();
                case 3 -> relatorioPorProfessor();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void relatorioPorTurma() {
        System.out.print("Código da disciplina da turma: ");
        String codigoDisciplina = scanner.nextLine();
        System.out.print("Semestre da turma (ex: 2024.1): ");
        String semestreTurma = scanner.nextLine();

        Turma turma = turmaService.buscarTurma(codigoDisciplina, semestreTurma);
        if (turma == null) {
            System.out.println("Turma não encontrada.");
            return;
        }

        System.out.println("\n--- RELATÓRIO DA TURMA: " + turma.getDisciplina().getNome() + " - " + turma.getSemestre() + " ---");
        System.out.println("Professor: " + turma.getProfessor().getNome());
        System.out.println("Horário: " + turma.getHorario());
        System.out.println("Presencial: " + (turma.isPresencial() ? "Sim (Sala: " + turma.getSala() + ")" : "Não"));
        System.out.println("Carga Horária da Disciplina: " + turma.getDisciplina().getCargaHoraria());
        System.out.println("Tipo de Avaliação: " + turma.getTipoAvaliacao());

        List<Avaliacao> avaliacoesTurma = avaliacaoService.listarPorTurma(codigoDisciplina, semestreTurma);
        if (avaliacoesTurma.isEmpty()) {
            System.out.println("Nenhuma avaliação lançada para esta turma ainda.");
            return;
        }

        System.out.println("\nAlunos na Turma:");
        for (Avaliacao avaliacao : avaliacoesTurma) {
            System.out.println("  - " + avaliacao.getAluno().getNome() + " (Matrícula: " + avaliacao.getAluno().getMatricula() + ")");
            System.out.println("    Média Final: " + String.format("%.2f", avaliacao.calcularMediaFinal()));
            System.out.println("    Frequência: " + String.format("%.2f", avaliacao.calcularFrequencia()) + "%");
            System.out.println("    Status: " + avaliacao.verificarAprovacao());
        }
    }

    private void relatorioPorDisciplina() {
        System.out.print("Código da disciplina: ");
        String codigoDisciplina = scanner.nextLine();
        Disciplina disciplina = turmaService.getDisciplinaService().buscarPorCodigo(codigoDisciplina); // Acessa o DisciplinaService pelo TurmaService
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }

        System.out.println("\n--- RELATÓRIO DA DISCIPLINA: " + disciplina.getNome() + " ---");
        List<Avaliacao> avaliacoesDisciplina = avaliacaoService.listarPorDisciplina(codigoDisciplina);
        if (avaliacoesDisciplina.isEmpty()) {
            System.out.println("Nenhuma avaliação lançada para esta disciplina ainda.");
            return;
        }

        // Agrupar por turma para melhor visualização
        avaliacoesDisciplina.stream()
                .collect(java.util.stream.Collectors.groupingBy(Avaliacao::getTurma))
                .forEach((turma, avaliacoesDaTurma) -> {
                    System.out.println("\n  --- Turma: " + turma.getSemestre() + " - Professor: " + turma.getProfessor().getNome() + " ---");
                    for (Avaliacao avaliacao : avaliacoesDaTurma) {
                        System.out.println("    - " + avaliacao.getAluno().getNome() + " (Matrícula: " + avaliacao.getAluno().getMatricula() + ")");
                        System.out.println("      Média Final: " + String.format("%.2f", avaliacao.calcularMediaFinal()));
                        System.out.println("      Frequência: " + String.format("%.2f", avaliacao.calcularFrequencia()) + "%");
                        System.out.println("      Status: " + avaliacao.verificarAprovacao());
                    }
                });
    }

    private void relatorioPorProfessor() {
        System.out.print("Nome do professor: ");
        String nomeProfessor = scanner.nextLine();
        Professor professor = professorService.buscarPorNome(nomeProfessor);
        if (professor == null) {
            System.out.println("Professor não encontrado.");
            return;
        }

        System.out.println("\n--- RELATÓRIO DO PROFESSOR: " + professor.getNome() + " ---");
        List<Avaliacao> avaliacoesProfessor = avaliacaoService.listarPorProfessor(nomeProfessor);
        if (avaliacoesProfessor.isEmpty()) {
            System.out.println("Nenhuma avaliação lançada para este professor ainda.");
            return;
        }

        // Agrupar por turma/disciplina para melhor visualização
        avaliacoesProfessor.stream()
                .collect(java.util.stream.Collectors.groupingBy(Avaliacao::getTurma))
                .forEach((turma, avaliacoesDaTurma) -> {
                    System.out.println("\n  --- Turma: " + turma.getDisciplina().getNome() + " - " + turma.getSemestre() + " ---");
                    for (Avaliacao avaliacao : avaliacoesDaTurma) {
                        System.out.println("    - " + avaliacao.getAluno().getNome() + " (Matrícula: " + avaliacao.getAluno().getMatricula() + ")");
                        System.out.println("      Média Final: " + String.format("%.2f", avaliacao.calcularMediaFinal()));
                        System.out.println("      Frequência: " + String.format("%.2f", avaliacao.calcularFrequencia()) + "%");
                        System.out.println("      Status: " + avaliacao.verificarAprovacao());
                    }
                });
    }


    private void exibirBoletimAluno() {
        System.out.print("Matrícula do aluno: ");
        String matriculaAluno = scanner.nextLine();
        Aluno aluno = alunoService.buscarPorMatricula(matriculaAluno);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("\n--- BOLETIM DO ALUNO: " + aluno.getNome() + " (" + aluno.getMatricula() + ") ---");
        List<Avaliacao> boletim = avaliacaoService.listarBoletimAluno(matriculaAluno);
        if (boletim.isEmpty()) {
            System.out.println("Nenhum registro de avaliação para este aluno.");
            return;
        }

        System.out.print("Deseja exibir os dados da turma (professor, tipo, carga horária)? (s/n): ");
        boolean incluirDadosTurma = scanner.nextLine().equalsIgnoreCase("s");

        // Agrupar por semestre
        boletim.stream()
                .collect(java.util.stream.Collectors.groupingBy(a -> a.getTurma().getSemestre()))
                .entrySet().stream()
                .sorted(java.util.Map.Entry.comparingByKey()) // Opcional: ordenar por semestre
                .forEach(entry -> {
                    String semestre = entry.getKey();
                    List<Avaliacao> avaliacoesNoSemestre = entry.getValue();
                    System.out.println("\nSemestre: " + semestre);
                    for (Avaliacao avaliacao : avaliacoesNoSemestre) {
                        System.out.println("  Disciplina: " + avaliacao.getTurma().getDisciplina().getNome() + " (" + avaliacao.getTurma().getDisciplina().getCodigo() + ")");
                        if (incluirDadosTurma) {
                            System.out.println("    Professor: " + avaliacao.getTurma().getProfessor().getNome());
                            System.out.println("    Tipo: " + (avaliacao.getTurma().isPresencial() ? "Presencial" : "Remota") +
                                    (avaliacao.getTurma().isPresencial() ? " (Sala: " + avaliacao.getTurma().getSala() + ")" : ""));
                            System.out.println("    Carga Horária: " + avaliacao.getTurma().getDisciplina().getCargaHoraria());
                        }
                        System.out.println("    Média Final: " + String.format("%.2f", avaliacao.calcularMediaFinal()));
                        System.out.println("    Frequência: " + String.format("%.2f", avaliacao.calcularFrequencia()) + "%");
                        System.out.println("    Status: " + avaliacao.verificarAprovacao());
                        System.out.println("    Notas: P1=" + (avaliacao.getP1() == -1 ? "N/A" : avaliacao.getP1()) +
                                ", P2=" + (avaliacao.getP2() == -1 ? "N/A" : avaliacao.getP2()) +
                                ", P3=" + (avaliacao.getP3() == -1 ? "N/A" : avaliacao.getP3()) +
                                ", L=" + (avaliacao.getListas() == -1 ? "N/A" : avaliacao.getListas()) +
                                ", S=" + (avaliacao.getSeminario() == -1 ? "N/A" : avaliacao.getSeminario()));
                        System.out.println("    Presenças: " + avaliacao.getPresencas() + "/" + avaliacao.getTotalAulas());
                    }
                });
    }
}
import java.util.List;
import java.util.Scanner;

public class DisciplinaUI {
    private final Scanner scanner = new Scanner(System.in);
    private DisciplinaService disciplinaService = new DisciplinaService();
    private List<Professor> professores;
    private TurmaService turmaService = new TurmaService();

    public void DisciplinaUI(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n--- MODO DISCIPLINA ---");
            System.out.println("1 - Cadastrar Disciplina");
            System.out.println("2 - Listar Disciplinas");
            System.out.println("3 - Salvar Disciplinas");
            System.out.println("4 - Carregar Disciplinas");
            System.out.println("5 - Cadastrar Turma para Disciplina");
            System.out.println("6 - Listar Turmas");
            System.out.println("0 - Voltar");
            System.out.print(">> ");
            opcao = scanner.nextInt(); scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarDisciplina();
                case 2 -> listarDisciplinas();
                case 3 -> salvar();
                case 4 -> carregar();
                case 5 -> cadastrarTurma();
                case 6 -> listarTurmas();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarDisciplina() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        if (disciplinaService.existeCodigo(codigo)) {
            System.out.println("Código já cadastrado.");
            return;
        }

        System.out.print("Carga horária: ");
        String carga = scanner.nextLine();

        Disciplina nova = new Disciplina(nome, codigo, carga);

        System.out.print("Deseja adicionar pré-requisitos? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            List<Disciplina> todas = disciplinaService.listarTodas();
            if (todas.isEmpty()) {
                System.out.println("Nenhuma disciplina disponível para pré-requisito.");
            } else {
                for (int i = 0; i < todas.size(); i++) {
                    System.out.println((i + 1) + " - " + todas.get(i).getNome());
                }

                int escolha;
                do {
                    System.out.print("Número da disciplina (0 para sair): ");
                    escolha = scanner.nextInt(); scanner.nextLine();
                    if (escolha >= 1 && escolha <= todas.size()) {
                        nova.adicionarPreRequisito(todas.get(escolha - 1));
                        System.out.println("Adicionado.");
                    }
                } while (escolha != 0);
            }
        }

        disciplinaService.adicionar(nova);
        System.out.println("Disciplina cadastrada.");
    }

    private void listarDisciplinas() {
        List<Disciplina> lista = disciplinaService.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }

        for (Disciplina d : lista) {
            System.out.println(d);
            if (!d.getPreRequisitos().isEmpty()) {
                System.out.println("Pré-requisitos:");
                for (Disciplina pre : d.getPreRequisitos()) {
                    System.out.println("- " + pre.getNome());
                }
            }
            System.out.println("--------------------");
        }
    }

    private void cadastrarTurma() {
        System.out.print("Código da disciplina: ");
        String codigo = scanner.nextLine();
        Disciplina disciplina = disciplinaService.buscarPorCodigo(codigo);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }

        System.out.println("Lista de professores:");
        for (int i = 0; i < professores.size(); i++) {
            System.out.println((i + 1) + " - " + professores.get(i).getNome());
        }

        System.out.print("Escolha o professor: ");
        int idx = scanner.nextInt(); scanner.nextLine();
        if (idx < 1 || idx > professores.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        Professor professor = professores.get(idx - 1);

        System.out.print("Semestre (ex: 2025.1): ");
        String semestre = scanner.nextLine();

        System.out.print("Tipo de avaliação (1 ou 2): ");
        int tipo = scanner.nextInt(); scanner.nextLine();

        System.out.print("É presencial? (s/n): ");
        boolean presencial = scanner.nextLine().equalsIgnoreCase("s");

        String sala = "-";
        if (presencial) {
            System.out.print("Sala: ");
            sala = scanner.nextLine();
        }

        System.out.print("Horário: ");
        String horario = scanner.nextLine();

        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt(); scanner.nextLine();

        Turma nova = new Turma(disciplina, professor, semestre, tipo, presencial, sala, horario, capacidade);
        turmaService.adicionar(nova);
        System.out.println("Turma cadastrada com sucesso.");
    }

    private void listarTurmas() {
        List<Turma> lista = turmaService.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
            return;
        }
        for (Turma t : lista) {
            System.out.println(t);
            System.out.println("---------------------");
        }
    }

    private void salvar() {
        disciplinaService.salvar(disciplinaService.getDisciplinas(), "disciplinas.txt");
    }

    private void carregar() {
        disciplinaService.limpar();
        disciplinaService.carregar(disciplinaService.getDisciplinas(), "disciplinas.txt");
    }
}

import java.util.Scanner;

public class SistemaPrincipal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DisciplinaService disciplinaService = new DisciplinaService();
        disciplinaService.inicializar();

        ProfessorService professorService = new ProfessorService();
        professorService.inicializar();

        TurmaService turmaService = new TurmaService();


        AlunoService alunoService = new AlunoService();
        alunoService.inicializar();

        AvaliacaoService avaliacaoService = new AvaliacaoService(alunoService, turmaService);
        avaliacaoService.inicializar();

        AlunoUI alunoUI = new AlunoUI(alunoService, disciplinaService);
        DisciplinaUI disciplinaUI = new DisciplinaUI(disciplinaService, professorService, turmaService);
        ProfessorUI professorUI = new ProfessorUI(professorService);
        AvaliacaoUI avaliacaoUI = new AvaliacaoUI(scanner, avaliacaoService, turmaService, alunoService, professorService);
        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Modo Aluno");
            System.out.println("2 - Modo Disciplina/Turma");
            System.out.println("3 - Modo Avaliação/Frequência");
            System.out.println("4 - Modo Professor");
            System.out.println("0 - Sair");
            System.out.print(">> ");
            opcao = scanner.nextInt(); scanner.nextLine();

            switch (opcao) {
                case 1 -> alunoUI.menu();
                case 2 -> disciplinaUI.menu();
                case 3 -> avaliacaoUI.menu();
                case 4 -> professorUI.menu();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}

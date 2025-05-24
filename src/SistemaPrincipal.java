import java.util.Scanner;

public class SistemaPrincipal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlunoUI alunoUI = new AlunoUI();
        DisciplinaUI disciplinaUI = new DisciplinaUI();

        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Modo Aluno");
            System.out.println("2 - Modo Disciplina/Turma");
            System.out.println("3 - Modo Avaliação/Frequência");
            System.out.println("0 - Sair");
            System.out.print(">> ");
            opcao = scanner.nextInt(); scanner.nextLine();

            switch (opcao) {
                case 1 -> alunoUI.menu();
                case 2 -> disciplinaUI.menu();
                case 3 -> System.out.println("Modo Avaliação ainda não implementado.");
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}

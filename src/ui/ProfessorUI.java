package ui;

import modelos.Professor;
import servicos.ProfessorService;

import java.util.List;
import java.util.Scanner;

public class ProfessorUI {
    private final Scanner scanner = new Scanner(System.in);
    private final ProfessorService professorService;

    public ProfessorUI(ProfessorService professorService) {
        this.professorService = professorService;
    }

    public void menu() {
        int opcao;
        do {
            System.out.println("\n--- MODO PROFESSOR ---");
            System.out.println("1 - Cadastrar Professor");
            System.out.println("2 - Listar Professores");
            System.out.println("3 - Salvar Professores");
            //System.out.println("4 - Carregar Professores");
            System.out.println("0 - Voltar");
            System.out.print(">> ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarProfessor();
                case 2 -> listarProfessores();
                case 3 -> professorService.salvar("professores.txt");
                //case 4 -> {
                    //professorService.carregar("professores.txt");
                    //System.out.println("Professores carregados com sucesso.");
                //}
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarProfessor() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matricula: ");
        String matricula = scanner.nextLine();
        System.out.print("area: ");
        String area = scanner.nextLine();

        Professor professor = new Professor(nome, matricula, area);
        professorService.adicionar(professor);
        System.out.println("Professor cadastrado com sucesso.");
    }

    private void listarProfessores() {
        List<Professor> lista = professorService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum professor cadastrado.");
            return;
        }
        System.out.println("\nLista de Professores:");
        for (Professor p : lista) {
            System.out.println(p);
        }
    }
}

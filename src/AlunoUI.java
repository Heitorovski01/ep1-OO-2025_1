import java.util.List;
import java.util.Scanner;


public class AlunoUI {
    private final AlunoService alunoService = new AlunoService();
    private final DisciplinaService disciplinaService = new DisciplinaService();
    private Scanner scanner = new Scanner(System.in);

    public void menu(){
        int opcao;

        do{
            System.out.println("\n--- MODO ALUNO ---");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Listar Alunos");
            System.out.println("3 - Trancar Disciplina");
            System.out.println("4 - Trancar Semestre");
            System.out.println("5 - Salvar Alunos");
            System.out.println("6 - Carregar Alunos");
            System.out.println("7 - Editar Aluno");
            System.out.println("8 - Matricular Aluno em Disciplina");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print(">> ");
            opcao = scanner.nextInt(); scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarAluno();
                case 2 -> listarAlunos();
                case 3 -> trancarDisciplina();
                case 4 -> trancarSemestre();
                case 5 -> salvarAlunos();
                case 6 -> carregarAlunos();
                case 7 -> editarAluno();
                case 8 -> matricularAlunoEmDisciplina();
                case 0 -> System.out.println("Saindo do modo Aluno...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    private void cadastrarAluno(){
        System.out.println("\n--- Cadastrar Aluno ---");
        System.out.println("Digite o nome do aluno: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a Matricula do aluno: ");
        String matricula = scanner.nextLine();

        if(alunoService.existeMatricula(matricula)){
            System.out.println("Esta matricula ja existe!");
            return;
        }

        System.out.println("Digite o curso do aluno: ");
        String curso = scanner.nextLine();

        System.out.println("Tipo [1] normal / [2] especial: ");
        int tipo = scanner.nextInt(); scanner.nextLine();

        Aluno aluno;
        if (tipo == 2) {
            aluno = new AlunoEspecial(nome, matricula, curso);
        } else {
            aluno = new AlunoNormal(nome, matricula, curso);
        }

        alunoService.adcionarAluno(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private void listarAlunos(){
        System.out.println("\n--- Listar Alunos ---");
        List<Aluno> alunos = alunoService.listarTodos();

        if(alunos.isEmpty()){
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        for(Aluno aluno : alunos){
            System.out.println(aluno);
            System.out.println("------------------------");
        }
    }

    private void trancarDisciplina(){
        System.out.println("\n--- Trancar Disciplina ---");
        System.out.println("Digite a matricula do aluno: ");
        String matricula = scanner.nextLine();

        Aluno aluno = alunoService.buscarPorMatricula(matricula);

        if(aluno == null){
            System.out.println("Aluno não encontrado.");
            return;
        }

        List<Disciplina> lista = aluno.getDisciplinasMatriculadas();
        if(lista.isEmpty()){
            System.out.println("Nenhuma disciplina para trancar.");
            return;
        }

        for(int i = 0; i < lista.size(); i++){
            System.out.println((i+1) + " - " + lista.get(i).getNome());
        }

        System.out.println("Escolha a disciplina a ser trancada: ");
        int opcaoTrancada = scanner.nextInt(); scanner.nextLine();

        if(opcaoTrancada < 1 || opcaoTrancada > lista.size()){
            System.out.println("Opção Inválida.");
            return;
        }

        Disciplina trancada = lista.get(opcaoTrancada-1);
        alunoService.trancarDisciplina(matricula, trancada);
        System.out.println("Disciplina trancada com sucesso!");
    }

    private void trancarSemestre(){
        System.out.println("\n--- Trancar Semestre ---");
        System.out.println("Digite a matricula do aluno: ");
        String matricula = scanner.nextLine();

        boolean sucesso = alunoService.trancarSemestre(matricula);
        if(sucesso){
            System.out.println("Semestre trancado com sucesso!");
        }else{
            System.out.println("Aluno não encontrado.");
        }
    }

    private void salvarAlunos(){
        alunoService.salvar(alunoService.getAlunos(), "alunos.txt");
    }

    private void carregarAlunos(){
        alunoService.limpar();
        alunoService.carregar(alunoService.getAlunos(), "alunos.txt");
    }

    private void editarAluno() {
        System.out.print("Matrícula do aluno a editar: ");
        String matricula = scanner.nextLine();

        Aluno aluno = alunoService.buscarPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("Editar dados do aluno: " + aluno.getNome());
        System.out.println("1 - Editar Nome");
        System.out.println("2 - Editar Curso");
        System.out.print(">> ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1 -> {
                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();
                aluno.setNome(novoNome);
                System.out.println("Nome atualizado.");
            }
            case 2 -> {
                System.out.print("Novo curso: ");
                String novoCurso = scanner.nextLine();
                aluno.setCurso(novoCurso);
                System.out.println("Curso atualizado.");
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    private void matricularAlunoEmDisciplina() {
        System.out.print("Matrícula do aluno: ");
        String matricula = scanner.nextLine();

        Aluno aluno = alunoService.buscarPorMatricula(matricula);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("Quantidade de disciplinas existentes: " + disciplinaService.getDisciplinas().size());
        System.out.print("Código da disciplina: ");
        String codigo = scanner.nextLine();

        Disciplina disciplina = disciplinaService.buscarPorCodigo(codigo);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }

        if (aluno.getDisciplinasMatriculadas().contains(disciplina)) {
            System.out.println("Aluno já matriculado nesta disciplina.");
            return;
        }

        int qtd = aluno.getDisciplinasMatriculadas().size() + 1;
        if (!aluno.podeMatricular(qtd)) {
            System.out.println("Este aluno não pode se matricular em mais disciplinas.");
            return;
        }

        for (Disciplina pre : disciplina.getPreRequisitos()) {
            if (!aluno.getDisciplinasMatriculadas().contains(pre)) {
                System.out.println("Aluno não possui o pré-requisito: " + pre.getNome());
                return;
            }
        }

        aluno.adicionarDisciplina(disciplina);
        System.out.println("Aluno matriculado com sucesso.");
    }
}

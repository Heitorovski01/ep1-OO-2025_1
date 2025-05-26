import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoService {
    private List<Aluno> alunos = new ArrayList<Aluno>();

    public void adcionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public boolean existeMatricula(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equals(matricula)) {
                return true;
            }
        }
        return false;
    }

    public Aluno buscarPorMatricula(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equals(matricula)) {
                return aluno;
            }
        }
        return null;
    }

    public boolean trancarDisciplina(String matricula, Disciplina disciplina) {
        Aluno aluno = buscarPorMatricula(matricula);
        if (aluno == null) return false;
        return aluno.getDisciplinasMatriculadas().remove(disciplina);
    }

    public boolean trancarSemestre(String matricula) {
        Aluno aluno = buscarPorMatricula(matricula);
        if (aluno == null) return false;
        aluno.getDisciplinasMatriculadas().clear();
        return true;
    }

    public List<Aluno> listarTodos() {
        return new ArrayList<>(alunos);
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void limpar() {
        alunos.clear();
    }

    public void salvar(List<Aluno> lista, String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (Aluno aluno : lista) {
                String tipo = (aluno instanceof AlunoEspecial) ? "especial" : "normal";
                writer.write(aluno.getNome() + ";" + aluno.getMatricula() + ";" + aluno.getCurso() + ";" + tipo);
                writer.newLine();
            }
            System.out.println("Alunos salvos em: " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }

    public void carregar(List<Aluno> lista, String caminho) {
        int novos = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 4) {
                    String nome = partes[0];
                    String matricula = partes[1];
                    String curso = partes[2];
                    String tipo = partes[3];

                    boolean existe = false;
                    for (Aluno a : lista) {
                        if (a.getMatricula().equalsIgnoreCase(matricula)) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Aluno novoAluno;
                        if (tipo.equalsIgnoreCase("especial")) {
                            novoAluno = new AlunoEspecial(nome, matricula, curso);
                        } else {
                            novoAluno = new AlunoNormal(nome, matricula, curso);
                        }
                        lista.add(novoAluno);
                        novos++;
                    }
                }
            }
            System.out.println("Alunos carregados: " + novos + " novos adicionados.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar alunos: " + e.getMessage());
        }
    }
}

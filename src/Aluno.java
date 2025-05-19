import java.util.ArrayList;
import java.util.List;

public abstract class Aluno extends Pessoa {
    private String curso;
    private List<Disciplina> disciplinasMatriculadas = new ArrayList<>();

    public Aluno(String nome, String matricula, String curso) {
        super(nome, matricula);
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public abstract boolean podeMatricular(int disciplinasQuantidade);


    public List<Disciplina> getDisciplinasMatriculadas() {
        return disciplinasMatriculadas;
    }
    public void adicionarDisciplina(Disciplina disciplina) {
        if (!disciplinasMatriculadas.contains(disciplina)) {
            disciplinasMatriculadas.add(disciplina);
        }
    }
    public void removerDisciplina(Disciplina disciplina) {
        disciplinasMatriculadas.remove(disciplina);
    }

    @Override
    public String toString() {
        return super.toString() + "\nCurso: " + curso;
    }
}

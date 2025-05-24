import java.util.ArrayList;
import java.util.List;

public class DisciplinaService {
    private final List<Disciplina> disciplinas = new ArrayList<>();

    public void adicionar(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public boolean existeCodigo(String codigo) {
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    public Disciplina buscarPorCodigo(String codigo) {
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) {
                return d;
            }
        }
        return null;
    }

    public List<Disciplina> listarTodas() {
        return new ArrayList<>(disciplinas);
    }

    public void limpar() {
        disciplinas.clear();
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }
}

import java.util.ArrayList;
import java.util.List;

    public class TurmaService {
        private final List<Turma> turmas = new ArrayList<>();
        private final DisciplinaService disciplinaService = new DisciplinaService();

        public void adicionar(Turma turma) {
            turmas.add(turma);
        }

        public List<Turma> listarTodas() {
            return new ArrayList<>(turmas);
        }

        public List<Turma> listarPorDisciplina(String codigoDisciplina) {
            List<Turma> resultado = new ArrayList<>();
            for (Turma t : turmas) {
                if (t.getDisciplina().getCodigo().equalsIgnoreCase(codigoDisciplina)) {
                    resultado.add(t);
                }
            }
            return resultado;
        }

        public void limpar() {
            turmas.clear();
        }

        public List<Turma> getTurmas() {
            return turmas;
        }
    }


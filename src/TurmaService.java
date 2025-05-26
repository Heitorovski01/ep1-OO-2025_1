import java.io.*;
import java.util.ArrayList;
import java.util.List;

    public class TurmaService {
        private final List<Turma> turmas = new ArrayList<>();
        private final DisciplinaService disciplinaService = new DisciplinaService();
        private Turma t = new Turma(null, null, null, 0, false, null, null, 0);

        public List<Turma> listarTodas() {
            return new ArrayList<>(turmas);
        }

        public Turma buscarTurma(String codigoDisciplina, String semestreTurma) {
            return turmas.stream()
                    .filter(t -> t.getDisciplina().getCodigo().equalsIgnoreCase(codigoDisciplina) &&
                            t.getSemestre().equalsIgnoreCase(semestreTurma))
                    .findFirst()
                    .orElse(null);
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

        public void adicionar(Turma turma) {
            turmas.add(turma);
        }

        public DisciplinaService getDisciplinaService() {
            return disciplinaService;
        }

        public void limpar() {
            turmas.clear();
        }

        public List<Turma> getTurmas() {
            return turmas;
        }
        public void salvar(List<Turma> lista, String caminho) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
                for (Turma t : lista) {
                    writer.write(t.getDisciplina().getCodigo() + ";" +
                            t.getProfessor().getNome() + ";" +
                            t.getSemestre() + ";" +
                            t.getTipoAvaliacao() + ";" +
                            t.isPresencial() + ";" +
                            t.getSala() + ";" +
                            t.getHorario() + ";" +
                            t.getCapacidadeMaxima());
                    writer.newLine();
                }
                System.out.println("Turmas salvas em: " + caminho);
            } catch (IOException e) {
                System.out.println("Erro ao salvar turmas: " + e.getMessage());
            }
        }

        public void carregar(List<Turma> lista, String caminho,
                             List<Disciplina> disciplinas, List<Professor> professores) {
            int novos = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] partes = linha.split(";");
                    if (partes.length == 8) {
                        String codDisc = partes[0];
                        String nomeProf = partes[1];
                        String semestre = partes[2];
                        int tipo = Integer.parseInt(partes[3]);
                        boolean presencial = Boolean.parseBoolean(partes[4]);
                        String sala = partes[5];
                        String horario = partes[6];
                        int capacidade = Integer.parseInt(partes[7]);

                        Disciplina disciplina = disciplinas.stream()
                                .filter(d -> d.getCodigo().equalsIgnoreCase(codDisc))
                                .findFirst().orElse(null);

                        Professor professor = professores.stream()
                                .filter(p -> p.getNome().equalsIgnoreCase(nomeProf))
                                .findFirst().orElse(null);

                        if (disciplina != null && professor != null) {
                            boolean existe = lista.stream().anyMatch(t ->
                                    t.getDisciplina().getCodigo().equalsIgnoreCase(codDisc)
                                            && t.getSemestre().equals(semestre)
                                            && t.getHorario().equalsIgnoreCase(horario));

                            if (!existe) {
                                lista.add(new Turma(disciplina, professor, semestre, tipo, presencial, sala, horario, capacidade));
                                novos++;
                            }
                        }
                    }
                }
                System.out.println("Turmas carregadas: " + novos + " novas adicionadas.");
            } catch (IOException e) {
                System.out.println("Erro ao carregar turmas: " + e.getMessage());
            }
        }

    }


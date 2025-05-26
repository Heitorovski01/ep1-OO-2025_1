import java.util.ArrayList;
import java.util.List;
import java.io.*;

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
        System.out.println("Listando disciplinas: " + disciplinas.size());
        for (Disciplina d : disciplinas) {
            System.out.println(d.getNome() + " (" + d.getCodigo() + ")");
        }
        return new ArrayList<>(disciplinas);
    }

    public void limpar() {
        disciplinas.clear();
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void salvar(List<Disciplina> lista, String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (Disciplina d : lista) {
                writer.write(d.getNome() + ";" + d.getCodigo() + ";" + d.getCargaHoraria());
                writer.newLine();
            }
            System.out.println("Disciplinas salvas em: " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }
    public void carregar(List<Disciplina> lista, String caminho) {
        int novas = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    String nome = partes[0];
                    String codigo = partes[1];
                    String carga = partes[2];

                    boolean existe = false;
                    for (Disciplina d : lista) {
                        if (d.getCodigo().equalsIgnoreCase(codigo)) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        lista.add(new Disciplina(nome, codigo, carga));
                        novas++;
                    }
                }
            }
            System.out.println("Disciplinas carregadas: " + novas + " novas adicionadas.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
    }
    public void inicializar() {
        carregar(getDisciplinas(), "disciplinas.txt");
    }
}

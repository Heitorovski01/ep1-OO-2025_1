package servicos;

import modelos.Professor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorService {
    private final List<Professor> professores = new ArrayList<>();

    public List<Professor> listarTodos() {
        return new ArrayList<>(professores);
    }

    public void adicionar(Professor professor) {
        professores.add(professor);
    }

    public void salvar(String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (Professor p : professores) {
                writer.write(p.getNome() + ";" + p.getArea() + ";" + p.getMatricula());
                writer.newLine();
            }
            System.out.println("Professores salvos em: " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao salvar professores: " + e.getMessage());
        }
    }

    public Professor buscarPorNome(String nome) {
        for (Professor p : professores) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                System.out.println(p);
            }
        }
        return null;
    }

    public void carregar(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int adicionados = 0;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 1) {
                    String nome = partes[0];
                    String departamento = partes.length > 1 ? partes[1] : "";
                    String email = partes.length > 2 ? partes[2] : "";

                    // Verifica se já existe um professor com esse nome
                    boolean existe = professores.stream()
                            .anyMatch(p -> p.getNome().equalsIgnoreCase(nome));

                    if (!existe) {
                        Professor p = new Professor(nome, departamento, email);
                        professores.add(p);
                        adicionados++;
                    }
                }
            }
            System.out.println(adicionados + " professores adicionados.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar professores: " + e.getMessage());
        }
    }
    public void inicializar() {
        carregar("professores.txt");
    }
}

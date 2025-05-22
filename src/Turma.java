import java.util.ArrayList;
import java.util.List;

public class Turma {
    private Disciplina disciplina;
    private Professor professor;
    private String semestre;
    private String horario;
    private boolean presencial;
    private String sala;
    private int capacidadeMaxima;

    private List<Aluno> alunosMatriculados = new ArrayList<>();

    public Turma(Disciplina disciplina, Professor professor, String semestre,
                 String horario, boolean presencial, String sala, int capacidadeMaxima) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.horario = horario;
        this.presencial = presencial;
        this.sala = sala;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public boolean matricular(Aluno aluno) {
        if(alunosMatriculados.size() >= capacidadeMaxima) {
            System.out.println("Capacidade máxima!");
            return false;
        }

        for (Disciplina pre : disciplina.getPreRequisitos()){
            if(!aluno.getDisciplinasMatriculadas().contains(pre)) {
                System.out.println("Aluno não possui o pré-requisito: " + pre.getNome());
                return false;
            }
        }

        alunosMatriculados.add(aluno);
        //aluno.adicionarDisciplina(disciplina);
        return true;
    }

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public String toString() {
        return "Turma de " + disciplina.getNome()
                + "\nProfessor: " + professor.getNome()
                + "\nSemestre: " + semestre
                + "\nHorário: " + horario
                + (presencial ? "\nPresencial - Sala: " + sala : "\nRemota")
                + "\nCapacidade: " + capacidadeMaxima
                + "\nMatriculados: " + alunosMatriculados.size();
    }
}

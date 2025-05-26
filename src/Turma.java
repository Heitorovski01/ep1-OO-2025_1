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
    private int tipo;

    private List<Aluno> alunosMatriculados = new ArrayList<>();

    public Turma(Disciplina disciplina, Professor professor, String semestre, int tipo, boolean presencial, String sala, String horario, int capacidade) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.tipo = tipo;
        this.horario = horario;
        this.presencial = presencial;
        this.sala = sala;
        this.capacidadeMaxima = capacidade;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getSemestre() {
        return semestre;
    }
    public int getTipoAvaliacao() {
        return tipo;
    }
    public boolean isPresencial() {
        return presencial;
    }
    public String getSala() {
        return sala;
    }
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
    public String getHorario() {
        return horario;
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

    public Disciplina getDisciplina() {
        return disciplina;
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

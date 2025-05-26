public class AlunoEspecial extends Aluno {
    public AlunoEspecial(String nome, String matricula, String curso) {
        super(nome, matricula, curso);
    }

    @Override
    public boolean podeMatricular(int disciplinasQuantidade){
        return disciplinasQuantidade <= 2;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDisciplinas: " + getDisciplinasMatriculadas() + "\n[Aluno especial]";
    }
}

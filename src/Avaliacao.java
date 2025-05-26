public class Avaliacao {
    private Aluno aluno;
    private Turma turma;
    private double p1;
    private double p2;
    private double p3;
    private double listas; // L
    private double seminario; // S
    private int totalAulas;
    private int presencas; // Número de presenças

    public Avaliacao(Aluno aluno, Turma turma, int totalAulas) {
        this.aluno = aluno;
        this.turma = turma;
        this.totalAulas = totalAulas;
        // Inicializa notas com -1
        this.p1 = -1.0;
        this.p2 = -1.0;
        this.p3 = -1.0;
        this.listas = -1.0;
        this.seminario = -1.0;
        this.presencas = 0;
    }

    // Getters e Setters
    public Aluno getAluno() {
        return aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }

    public double getP3() {
        return p3;
    }

    public void setP3(double p3) {
        this.p3 = p3;
    }

    public double getListas() {
        return listas;
    }

    public void setListas(double listas) {
        this.listas = listas;
    }

    public double getSeminario() {
        return seminario;
    }

    public void setSeminario(double seminario) {
        this.seminario = seminario;
    }

    public int getTotalAulas() {
        return totalAulas;
    }

    public void setTotalAulas(int totalAulas) {
        this.totalAulas = totalAulas;
    }

    public int getPresencas() {
        return presencas;
    }

    public void setPresencas(int presencas) {
        this.presencas = presencas;
    }

    // Métodos para cálculo da média final e frequência
    public double calcularMediaFinal() {
        if (p1 < 0 || p2 < 0 || p3 < 0 || listas < 0 || seminario < 0) {
            return -1.0; //
        }

        if (turma.getTipoAvaliacao() == 1) {
            return (p1 + p2 + p3 + listas + seminario) / 5.0;
        } else if (turma.getTipoAvaliacao() == 2) {
            return (p1 + p2 * 2 + p3 * 3 + listas + seminario) / 8.0;
        }
        return -1.0; //
    }

    public double calcularFrequencia() {
        if (totalAulas == 0) {
            return 0.0;
        }
        return (double) presencas / totalAulas * 100.0;
    }

    public String verificarAprovacao() {
        double media = calcularMediaFinal();
        double frequencia = calcularFrequencia();

        if (media < 0) {
            return "Notas incompletas";
        }
        if (media >= 5.0 && frequencia >= 75.0) {
            return "Aprovado";
        } else if (media < 5.0) {
            return "Reprovado por Nota";
        } else {
            return "Reprovado por Falta";
        }
    }

    @Override
    public String toString() {
        return "Avaliação para Aluno: " + aluno.getNome() + " (Matrícula: " + aluno.getMatricula() + ")" +
                "\n  Turma: " + turma.getDisciplina().getNome() + " - " + turma.getSemestre() +
                "\n  P1: " + (p1 == -1 ? "N/A" : p1) +
                ", P2: " + (p2 == -1 ? "N/A" : p2) +
                ", P3: " + (p3 == -1 ? "N/A" : p3) +
                ", Listas: " + (listas == -1 ? "N/A" : listas) +
                ", Seminário: " + (seminario == -1 ? "N/A" : seminario) +
                "\n  Total de Aulas: " + totalAulas + ", Presenças: " + presencas +
                "\n  Média Final: " + String.format("%.2f", calcularMediaFinal()) +
                ", Frequência: " + String.format("%.2f", calcularFrequencia()) + "%" +
                "\n  Status: " + verificarAprovacao();
    }
}
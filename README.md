# Sistema Acadêmico - FCTE

## Descrição do Projeto

Desenvolvimento de um sistema acadêmico para gerenciar alunos, disciplinas, professores, turmas, avaliações e frequência, utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

O enunciado do trabalho pode ser encontrado aqui:
- [Trabalho 1 - Sistema Acadêmico](https://github.com/lboaventura25/OO-T06_2025.1_UnB_FCTE/blob/main/trabalhos/ep1/README.md)

## Dados do modelos.Aluno

- **Nome completo:** Heitor Pinheiro Gonçalves das Chagas
- **Matrícula:** 242028682
- **Curso:** Engenharias
- **modelos.Turma:** 06

---

## Instruções para Compilação e Execução

1. **Compilação:**  
   1° Baixe a pasta src
   2° Abra o terminal de sua preferência
   3°  cd "C:\Users\(seu usuario)\Downloads\src\Sistema"
   4° javac -d bin src\modelos\*.java src\servicos\*.java src\ui\*.java src\main\*.java
   

3. **Execução:**  
   1° java -cp bin main.SistemaPrincipal

4. **Estrutura de Pastas:**  
   ├── modelos/ # Classes de domínio (entidades do sistema)
│ ├── Aluno.java
│ ├── AlunoEspecial.java
│ ├── AlunoNormal.java
│ ├── Avaliacao.java
│ ├── Disciplina.java
│ ├── Pessoa.java
│ ├── Professor.java
│ └── Turma.java
│
├── servicos/ # Lógica de negócio e persistência
│ ├── AlunoService.java
│ ├── AvaliacaoService.java
│ ├── DisciplinaService.java
│ ├── ProfessorService.java
│ └── TurmaService.java
│
├── ui/ # Interfaces de usuário (menu e entrada de dados)
│ ├── AlunoUI.java
│ ├── AvaliacaoUI.java
│ ├── DisciplinaUI.java
│ └── ProfessorUI.java
│
├── main/ # Classe principal do sistema
│ └── SistemaPrincipal.java
│
├── disciplinas.txt # Arquivos de persistência
├── professores.txt
├── alunos.txt
├── turmas.txt

3. **Versão do JAVA utilizada:**  
   Java 21

---

## Vídeo de Demonstração

- [Inserir o link para o vídeo no YouTube/Drive aqui]

---

## Prints da Execução

1. Menu Principal:  
   \PrintMenuPrincipal.png\

2. Cadastro de Aluno:  
   \PrintCadastroDeAlunos.png\

3. Relatório de Frequência/Notas:  


---

## Principais Funcionalidades Implementadas

- [OK] Cadastro, listagem, matrícula e trancamento de alunos (Normais e Especiais)
- [OK ] Cadastro de disciplinas e criação de turmas (presenciais e remotas)
- [OK ] Matrícula de alunos em turmas, respeitando vagas e pré-requisitos
- [OK ] Lançamento de notas e controle de presença
- [OK ] Cálculo de média final e verificação de aprovação/reprovação
- [OK ] Relatórios de desempenho acadêmico por aluno, turma e disciplina
- [OK ] Persistência de dados em arquivos (.txt ou .csv)
- [OK ] Tratamento de duplicidade de matrículas
- [OK ] Uso de herança, polimorfismo e encapsulamento

---

## Observações (Extras ou Dificuldades)

- [Espaço para o aluno comentar qualquer funcionalidade extra que implementou, dificuldades enfrentadas, ou considerações importantes.]

---

## Contato

- Heitorbsbdf@gmail.com

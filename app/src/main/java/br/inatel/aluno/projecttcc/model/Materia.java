package br.inatel.aluno.projecttcc.model;

public class Materia {
    private Long id;
    private String nome;
    private String sigla;
    private String turma;

    public Materia(Long id, String nome, String sigla, String turma){
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.turma = turma;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.example.UNKNOWN.myapplication.backend.model;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.List;

@Entity
public class Materia {
    @Id
    private Long id;
    private String nome;
    private String sigla;

    private Long professor;
    private List<Long> alunos;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfessor() {
        return professor;
    }

    public void setProfessor(Long professor) {
        this.professor = professor;
    }

    public List<Long> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Long> alunos) {
        this.alunos = alunos;
    }
}

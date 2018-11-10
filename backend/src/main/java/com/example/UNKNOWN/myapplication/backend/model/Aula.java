package com.example.UNKNOWN.myapplication.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
public class Aula {
    @Id
    private Long id;
    @Index
    private Long materia;

    private Date dataAula;

    private String info;
    private Long raio;

    private String horaStart;
    private String horaEnd;

    private double latitude;
    private double longitude;

    // 0 to started
    // 1 to closed
    @Index
    private Long status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMateria() {
        return materia;
    }

    public void setMateria(Long materia) {
        this.materia = materia;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getDataAula() {
        return dataAula;
    }

    public void setDataAula(Date dataAula) {
        this.dataAula = dataAula;
    }

    public String getHoraStart() {
        return horaStart;
    }

    public void setHoraStart(String horaStart) {
        this.horaStart = horaStart;
    }

    public String getHoraEnd() {
        return horaEnd;
    }

    public void setHoraEnd(String horaEnd) {
        this.horaEnd = horaEnd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getRaio() {
        return raio;
    }

    public void setRaio(Long raio) {
        this.raio = raio;
    }
}

package br.inatel.aluno.projecttcc.model;


import java.util.Date;

public class Aula {
    private Long id;
    private Long materia;
    private Date dataAula;
    private String info;
    private String horaStart;
    private String horaEnd;
    private Long raio;
    private double latitude;
    private double longitude;
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

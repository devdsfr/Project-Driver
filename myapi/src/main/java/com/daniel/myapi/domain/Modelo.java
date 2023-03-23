package com.daniel.myapi.domain;

import javax.persistence.*;
@Entity
@Table(name = "modelo")
public class Modelo {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "fipe_id")
    private int fipe_id;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    public String getId() {
        return id;
    }

    public Marca getMarca() {
        return marca;
    }
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFipe_id() {
        return fipe_id;
    }

    public void setFipe_id(int fipe_id) {
        this.fipe_id = fipe_id;
    }
}

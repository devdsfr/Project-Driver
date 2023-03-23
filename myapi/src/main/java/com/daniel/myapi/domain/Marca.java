package com.daniel.myapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "marca")
public class Marca {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "fipe_id")
    private int fipe_id;

    public String getId() {
        return id;
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

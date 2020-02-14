package com.accenture.flowershop.be.entity.common;


import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

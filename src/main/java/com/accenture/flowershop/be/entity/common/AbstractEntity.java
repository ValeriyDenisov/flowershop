package com.accenture.flowershop.be.entity.common;


import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !o.getClass().equals(this.getClass())) return false;
        AbstractEntity that = (AbstractEntity) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

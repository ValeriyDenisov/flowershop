package com.accenture.flowershop.be.entity.address;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address extends AbstractEntity {

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "building", nullable = false)
    private Integer building;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + (id != null ? id : "") +
                ", street=" + street +
                ", city=" + city +
                ", code=" + code +
                ", building=" + building +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String street;
        private String city;
        private Integer code;
        private Integer building;

        public Builder(String street, String city, Integer code, Integer building) {
            this.street = street;
            this.city = city;
            this.code = code;
            this.building = building;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    private Address(Builder builder) {
        this.street = builder.street;
        this.city = builder.city;
        this.code = builder.code;
        this.building = builder.building;
        this.id = builder.id;
    }

    public Address() {

    }
}

package com.accenture.flowershop.be.entity.address;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address extends AbstractEntity {

    @Column(name = "street", nullable = false)
    @NotBlank(message = "Street name is empty!")
    @Size(min = 2, max = 50, message = "Size of street name is incorrect!")
    private String street;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "City name is empty!")
    @Size(min = 2, max = 50, message = "Size of city name is incorrect!")
    private String city;

    @Column(name = "code", nullable = false)
    @NotNull(message = "Address code is null!")
    @Positive(message = "Address code is negative number!")
    private Integer code;

    @Column(name = "building", nullable = false)
    @NotNull(message = "Building number is null!")
    @Positive(message = "Building number mis negative!")
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
        StringBuilder builder = new StringBuilder();
        builder
                .append("Address{")
                .append("id=").append(id != null ? id : "")
                .append(", street= ").append(street)
                .append(", city= ").append(city)
                .append(", code= ").append(code)
                .append(", building= ").append(building)
                .append("}");
        return builder.toString();
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

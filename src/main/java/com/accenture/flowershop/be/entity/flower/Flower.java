package com.accenture.flowershop.be.entity.flower;

import com.accenture.flowershop.be.entity.common.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "flowers")
public class Flower extends AbstractEntity {
    public static final String NAME = "name";

    @Column(name = "name")
    @NotBlank(message = "Flower name is empty!")
    @Size(min = 2, max = 50, message = "Flower name size is incorrect!")
    private String name;

    @Column(name = "price")
    @NotNull(message = "Flower price is null!")
    @Positive(message = "Flower price is negative!")
    private Double price;

    @Column(name = "quantity_in_stock")
    @NotNull(message = "Flower quantity in stock is null!")
    @Min(value = 0, message = "Customer discount is negative!")
    private Integer quantityInStock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("Flower{")
                .append("id=").append(id != null ? id : "")
                .append(", name=").append(name)
                .append(", price=").append(price)
                .append(", quantityInStock=").append(quantityInStock)
                .append("}");
        return builder.toString();
    }

    public static class Builder {
        private Integer id;
        private String name;
        private Double price;
        private Integer quantityInStock;

        public Builder(String name, Double price, Integer quantityInStock) {
            this.name = name;
            this.price = price;
            this.quantityInStock = quantityInStock;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Flower build() {
            return new Flower(this);
        }
    }

    private Flower(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantityInStock = builder.quantityInStock;
        this.id = builder.id;
    }

    public Flower() {
    }

}

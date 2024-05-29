package hu.garaba.webshop.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

// Getter/setter = property
// POJO = Plain Old Java Object
// Bean

/*
    JPA specification (concrete library=Hibernate)
        ORM = Object Relational Mapping

 */

@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "id")
    @SequenceGenerator(allocationSize=1, name="MySequenceGenerator", sequenceName = "item_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MySequenceGenerator")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    private String description;

    /////////// GETTERS/SETTERS


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(price, item.price) && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, description);
    }
}
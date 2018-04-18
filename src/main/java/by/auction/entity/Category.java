package by.auction.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Mapping entity to DB table "categories"
 */
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "name", nullable = false, unique = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

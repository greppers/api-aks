package tn.esprit.pidev.Entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name="product_category")
public class ProductCategory implements Serializable {
    // FOOD(1, "FOOD"),
    // HEALTH_AND_COSMETICS(2, "HEALTH AND COSMETICS"),
    // CLOTHING_AND_ACCESSORIES(3, "CLOTHING AND ACCESSORIES");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    @OneToMany(cascade = CascadeType.ALL)
    Set<ProductSubcategory> productSubcategories;
    String icon;
    @Override
    public String toString() {
        return "ProductCategory [id=" + id + ", name=" + name + ", productSubcategories=" + productSubcategories
                + ", icon=" + icon + "]";
    }
}
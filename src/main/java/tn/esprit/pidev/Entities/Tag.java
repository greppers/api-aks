package tn.esprit.pidev.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "tags")
public class Tag implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(unique = true)
    String name;
    String icon;
    // Subcategories for FOOD_AND_EDIBLE category
    // ORGANIC("ORGANIC"),
    // GLUTEN_FREE("GLUTEN-FREE"),
    // DAIRY_FREE("DAIRY-FREE"),
    // VEGAN("VEGAN"),
    // SNACKS("SNACKS");

}

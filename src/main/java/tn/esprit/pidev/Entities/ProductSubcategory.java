package tn.esprit.pidev.Entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product_subcategory")
public class ProductSubcategory implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String icon;
    @OneToMany
    Set<Product> products;
}
    // FRUITS_AND_VEGETABLES(1, "FRUITS AND VEGETABLES", ProductCategories.FOOD),
    // HERBS_AND_SEASONING(2, "HERBS AND SEASONING", ProductCategories.FOOD),
    // MILK_AND_DAIRIES(3, "MILK AND DAIRIES", ProductCategories.FOOD),
    // PET_AND_ANIMAL_FOOD(4, "PET AND ANIMAL FOOD", ProductCategories.FOOD),
    // FRESH_FRUIT(5, "FRESH FRUIT", ProductCategories.FOOD),
    // SWEETS_AND_CAKES(6, "SWEETS AND CAKES", ProductCategories.FOOD),

    // VITAMINS_AND_SUPPLEMENTS(7, "VITAMINS AND SUPPLEMENTS",
    // ProductCategories.HEALTH_AND_COSMETICS),
    // SKINCARE(8, "SKINCARE", ProductCategories.HEALTH_AND_COSMETICS),
    // HAIRCARE(9, "HAIRCARE", ProductCategories.HEALTH_AND_COSMETICS),
    // PERSONAL_CARE(10, "PERSONAL CARE", ProductCategories.HEALTH_AND_COSMETICS),
    // ESSENTIAL_OILS(11, "ESSENTIAL OILS", ProductCategories.HEALTH_AND_COSMETICS),
    // MAKEUP(12, "MAKEUP", ProductCategories.HEALTH_AND_COSMETICS),

    // ACCESSOIRIES(13, "ACCESSOIRIES", ProductCategories.CLOTHING_AND_ACCESSORIES),
    // SHOES(14, "SHOES", ProductCategories.CLOTHING_AND_ACCESSORIES),
    // TOPS(15, "TOPS", ProductCategories.CLOTHING_AND_ACCESSORIES),
    // TEXTILE(16, "TEXTILE", ProductCategories.CLOTHING_AND_ACCESSORIES);
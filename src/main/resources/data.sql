INSERT INTO
    product_category (name)
VALUES ('FOOD'), ('HEALTH_AND_COSMETICS'), ('CLOTHING_AND_ACCESSORIES');

INSERT INTO
    product_subcategory (name)
VALUES ('FRUITS_AND_VEGETABLES'), ('HERBS_AND_SEASONING'), ('MILK_AND_DAIRIES'), ('PET_AND_ANIMAL_FOOD'), ('FRESH_FRUIT'), ('COFFEE'), ('SWEETS_AND_CAKES');

INSERT INTO
    product_subcategory (name)
VALUES ('VITAMINS_AND_SUPPLEMENTS'), ('SKINCARE'), ('HAIRCARE'), ('PERSONAL_CARE'), ('ESSENTIAL_OILS'), ('MAKEUP');

INSERT INTO
    product_subcategory (name)
VALUES ('ACCESSOIRIES'), ('SHOES'), ('TOPS'), ('TEXTILE'), ('CARPETS');

INSERT INTO
    roles (name)
VALUES ('Merchant'), ('Client'), ('Admin');

INSERT INTO
    product_category_product_subcategories (
        product_category_id,
        product_subcategories_id
    )
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (2, 7), (2, 8), (2, 9), (2, 10), (2, 11), (2, 12), (3, 13), (3, 14), (3, 15), (3, 16), (3, 17);
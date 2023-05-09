package tn.esprit.pidev.Services;

import tn.esprit.pidev.Entities.ProductCategory;
import tn.esprit.pidev.Entities.ProductSubcategory;
import tn.esprit.pidev.Repositories.ProductCategoryRepository;
import tn.esprit.pidev.Repositories.ProductSubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSubcategoryService {

    @Autowired
    private ProductSubcategoryRepository productSubcategoryRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductSubcategory> getAllProductSubcategories() {
        return (List<ProductSubcategory>) productSubcategoryRepository.findAll();
    }

    public Optional<ProductSubcategory> getProductSubcategoryById(long id) {
        return productSubcategoryRepository.findById(id);
    }

    public ProductSubcategory saveProductSubcategory(ProductSubcategory productSubcategory) {
        return productSubcategoryRepository.save(productSubcategory);
    }


    public void deleteProductSubcategoryById(long id) {
        productSubcategoryRepository.deleteById(id);
    }
}

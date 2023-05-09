package tn.esprit.pidev.Services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.pidev.Entities.ProductCategory;
import tn.esprit.pidev.Entities.ProductSubcategory;
import tn.esprit.pidev.Repositories.ProductCategoryRepository;

@Service
public class ProductCategoryService implements Serializable {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private IObjectStorageService objectStorageService;

    public List<ProductCategory> getAllProductCategories() {
        try {
            return productCategoryRepository.findAll();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ProductCategory getProductCategoryById(int id) {
        return productCategoryRepository.findById((long) id).orElse(null);
    }

    public ProductCategory saveProductCategory(ProductCategory productCategory,MultipartFile iconFile) {
        if (iconFile!=null) {
            String link=objectStorageService.saveFileAlone(iconFile, "icons/"+productCategory.getName());
            productCategory.setIcon(link);
        }
        return productCategoryRepository.save(productCategory);
    }

    public void deleteProductCategoryById(int id) {
        productCategoryRepository.deleteById((long) id);
    }

    public ProductCategory updateProductCategory(int id, ProductCategory updatedProductCategory) {
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById((long) id);
        if (optionalProductCategory.isPresent()) {
            ProductCategory productCategory = optionalProductCategory.get();
            productCategory.setName(updatedProductCategory.getName());
            return productCategoryRepository.save(productCategory);
        } else {
            throw new EntityNotFoundException("Entity not found with id: " + id);
        }
    }

    public ProductCategory findProductCategoryByIdAndAssignCategory(Long id, ProductSubcategory productSubcategory) {
        try {
            Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
            if (productCategory.isPresent()) {
                productCategory.get().getProductSubcategories().add(productSubcategory);
            } else {
                throw new EntityNotFoundException("Entity not found with id: " + productSubcategory);
            }
            return productCategoryRepository.save(productCategory.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ProductCategory saveSubcategoryWithCategory(Long productCategoryId,
            ProductSubcategory productSubcategory) {
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(productCategoryId);
        if (optionalProductCategory.isPresent()) {
            ProductCategory productCategory = optionalProductCategory.get();
            productCategory.getProductSubcategories().add(productSubcategory);
            return productCategoryRepository.save(productCategory);
        } else
            return null;
        // return productSubcategoryRepository.save(productSubcategory);
    }
}

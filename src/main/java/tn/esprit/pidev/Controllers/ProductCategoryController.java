package tn.esprit.pidev.Controllers;

import tn.esprit.pidev.Entities.ProductCategory;
import tn.esprit.pidev.Entities.ProductSubcategory;
import tn.esprit.pidev.Services.ProductCategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        try {
            List<ProductCategory> productCategories = productCategoryService.getAllProductCategories();
            return new ResponseEntity<>(productCategories, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable int id) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
        return new ResponseEntity<>(productCategory, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ProductCategory> createProductCategory(@RequestParam(value = "category") String categoryJson,
            @RequestParam(value = "icon", required = false) MultipartFile icon)
            throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCategory category = objectMapper.readValue(categoryJson, ProductCategory.class);
        ProductCategory createdProductCategory = productCategoryService.saveProductCategory(category,icon);
        return new ResponseEntity<>(createdProductCategory, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductCategory> saveSubcategoryWithCategory(@PathVariable("id") long id,
            @RequestBody ProductSubcategory productSubcategory) {
        ProductCategory savedProductSubcategory = productCategoryService
                .saveSubcategoryWithCategory(id, productSubcategory);
        return new ResponseEntity<>(savedProductSubcategory, HttpStatus.CREATED);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable
    // int id,
    // @RequestBody ProductCategory productCategory) {
    // ProductCategory updatedProductCategory =
    // productCategoryService.updateProductCategory(id, productCategory);
    // return new ResponseEntity<>(updatedProductCategory, HttpStatus.OK);
    // }
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> assignSubcategoryToCategory(@PathVariable Long id,
            @RequestBody ProductSubcategory productSubcategory) {
        ProductCategory updatedProductCategory = productCategoryService.findProductCategoryByIdAndAssignCategory(id,
                productSubcategory);
        return new ResponseEntity<>(updatedProductCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable int id) {
        productCategoryService.deleteProductCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

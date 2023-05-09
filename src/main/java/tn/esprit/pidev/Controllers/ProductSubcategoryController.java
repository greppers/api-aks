package tn.esprit.pidev.Controllers;

import tn.esprit.pidev.Entities.ProductCategory;
import tn.esprit.pidev.Entities.ProductSubcategory;
import tn.esprit.pidev.Services.ProductSubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subcategories")
public class ProductSubcategoryController {

    @Autowired
    private ProductSubcategoryService productSubcategoryService;

    @GetMapping
    public ResponseEntity<List<ProductSubcategory>> getAllProductSubcategories() {
        List<ProductSubcategory> productSubcategories = productSubcategoryService.getAllProductSubcategories();
        return new ResponseEntity<>(productSubcategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSubcategory> getProductSubcategoryById(@PathVariable("id") long id) {
        Optional<ProductSubcategory> productSubcategory = productSubcategoryService.getProductSubcategoryById(id);
        if (productSubcategory.isPresent()) {
            return new ResponseEntity<>(productSubcategory.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ProductSubcategory> saveProductSubcategory(
            @RequestBody ProductSubcategory productSubcategory) {
        ProductSubcategory savedProductSubcategory = productSubcategoryService
                .saveProductSubcategory(productSubcategory);
        return new ResponseEntity<>(savedProductSubcategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductSubcategory> updateProductSubcategory(@PathVariable("id") long id,
            @RequestBody ProductSubcategory productSubcategory) {
        Optional<ProductSubcategory> existingProductSubcategory = productSubcategoryService
                .getProductSubcategoryById(id);
        if (existingProductSubcategory.isPresent()) {
            productSubcategory.setId(id);
            ProductSubcategory savedProductSubcategory = productSubcategoryService
                    .saveProductSubcategory(productSubcategory);
            return new ResponseEntity<>(savedProductSubcategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductSubcategoryById(@PathVariable("id") long id) {
        productSubcategoryService.deleteProductSubcategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

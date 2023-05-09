package tn.esprit.pidev.Services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import tn.esprit.pidev.Entities.Product;

public interface IProductService {

    List<Product> findAll();

    Optional<Product> findById(long id);

    Product save(Product product);

    Product updateProduct(long id, Product updatedProduct);

    void deleteById(long id);
    void deleteProductWithReference(Long productId) ;
    Product createProductAndAssignToStore(Long storeId, Product product, List<MultipartFile> multipartFiles)
            throws IOException ;
    // Product createProductAndAssignToStore(Long storeId, Product productDto,List<MultipartFile> multipartFiles) throws IOException;

}
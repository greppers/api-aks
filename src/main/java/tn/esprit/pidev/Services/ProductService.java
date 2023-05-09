package tn.esprit.pidev.Services;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import tn.esprit.pidev.Entities.Store;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.util.Objects;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import tn.esprit.pidev.Entities.Carcirogenic;
import tn.esprit.pidev.Entities.Composition;
import tn.esprit.pidev.Entities.Product;
import tn.esprit.pidev.Entities.Tag;
import tn.esprit.pidev.Repositories.CarcirogenicRepository;
import tn.esprit.pidev.Repositories.CompositionRepository;
import tn.esprit.pidev.Repositories.ProductRepository;
import tn.esprit.pidev.Repositories.StoreRepository;
import tn.esprit.pidev.Repositories.TagRepository;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    CompositionRepository compositionRepository;
    @Autowired
    IObjectStorageService objectStorageService;
    @Autowired
    CarcirogenicRepository carcirogenicRepository;
    // @Value("${do.space.bucket}")

    String FOLDER = "products/";

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(long id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setDescription(updatedProduct.getDescription());
            // existingProduct.setStore(updatedProduct.getStore());
            existingProduct.setCompositions(updatedProduct.getCompositions());
            existingProduct.setQuantity(updatedProduct.getQuantity());
            existingProduct.setAvailable(updatedProduct.isAvailable());
            existingProduct.setBioScore(updatedProduct.getBioScore());
            return productRepository.save(existingProduct);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    public void deleteProductWithReference(Long productId) {
        // Retrieve the product that you want to delete
        Product product = productRepository.findById(productId).orElse(null);
        // product.getImageUrls().clear();

        if (product != null) {
            // Retrieve the stores that reference the product
            List<Store> stores = storeRepository.findByProducts_Id(productId);

            // Remove the product from the set of products in the stores
            for (Store store : stores) {
                store.getProducts().remove(product);
                storeRepository.save(store);
            }
            // product.getTags().forEach(tag->product.getTags().remove(tag));
            product.getTags().clear();
            product.getImageUrls().forEach(image -> objectStorageService.deleteFile(image));
            product.getImageUrls().clear();
            // product.getImageUrls().forEach(image->product.getImageUrls().remove(image));
            // Delete the product
            productRepository.delete(product);
        }
    }
    @Override
    public Product createProductAndAssignToStore(Long storeId, Product product, List<MultipartFile> multipartFiles)
            throws IOException {
        // Retrieve the store from the repository
        List<Carcirogenic> carcirogenics = carcirogenicRepository.findAll();
        product.setBioScore(100);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with ID: " + storeId));
                System.out.println(store.getName());
        if (product.getCompositions() != null) 
        for (Composition composition : product.getCompositions()) {
            Carcirogenic carcirogenicFound = carcirogenics.stream()
                    .filter(carcirogenic -> composition.getName().toLowerCase()
                            .contains(carcirogenic.getName().toLowerCase()))
                    .findAny().orElse(null);
            if (carcirogenicFound != null) {
                product.setBioScore(
                        product.getBioScore() - carcirogenicFound.getToxicityScore() *
                                composition.getQuantity());
            }
        }
        compositionRepository.saveAll(product.getCompositions());
        if (product.getBioScore() < 20) {
            return null;
        }
        if (product.getQuantity() > 0)
            product.setAvailable(true);
        if (product.getImageUrls() == null) {
            product.setImageUrls(new HashSet<String>());
        }
        HashSet<Tag> tagsToIterate = new HashSet<Tag>();
        tagsToIterate.addAll(product.getTags());
        tagsToIterate.forEach(tag -> {
            Tag exist = tagRepository.findFirstByName(tag.getName());
            if (exist != null) {
                product.getTags().remove(tag);
                product.getTags().add(exist);
                return;
            }
            tagRepository.save(tag);
        });
        String path=FOLDER+product.getName().replaceAll(" ", "-").toLowerCase()+"/";
        if (multipartFiles != null)
        multipartFiles.forEach(image -> {
            String imgUrl = objectStorageService.saveFileAlone((MultipartFile) image, path);
            product.getImageUrls().add(imgUrl);
        });
        if ( product.getId()!=0L) {
            return productRepository.save(product);
        }

        store.getProducts().add(product);
        System.out.println(store.getProducts().toString());
        storeRepository.save(store);

        return product;
    }

}

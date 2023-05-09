package tn.esprit.pidev.Services;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.pidev.Entities.Product;
import tn.esprit.pidev.Entities.Store;
import tn.esprit.pidev.Entities.Vendor;
import tn.esprit.pidev.Repositories.ProductRepository;
import tn.esprit.pidev.Repositories.StoreRepository;
import tn.esprit.pidev.Repositories.VendorRepository;

@Service
public class StoreService implements IStoreService {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IObjectStorageService objectStorageService;
    // @Value("${do.space.bucket}")
    String FOLDER = "stores/";

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public Store createOrUpdateStore(Long userId, Store store, MultipartFile logo, MultipartFile storeImage) {
        try {
            Vendor vendor = null;
            if (userId != null)
                vendor = vendorRepository.findVendorByUserId(userId).get();
            String path = FOLDER + store.getName().replaceAll(" ", "-").toLowerCase() + "/";
            String logoUrl = objectStorageService.saveFileAlone((MultipartFile) logo, path);
            store.setLogo(logoUrl);
            String storeImageUrl = objectStorageService.saveFileAlone((MultipartFile) storeImage, path);
            store.setStoreImage(storeImageUrl);
            store.setApproved(true);
            // return storeRepository.save(store);
            if (vendor != null) {
                vendor.getStores().add(store);
                vendorRepository.save(vendor);
                return store;
            }
            return storeRepository.save(store);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Store updateStore(long id, Store store, MultipartFile logo, MultipartFile storeImage) {
        String path = FOLDER + store.getName().replaceAll(" ", "-").toLowerCase() + "/";
        Store storeToEdit = storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Store not found with ID: "));
        if (logo != null) {
            objectStorageService.deleteFile(storeToEdit.getLogo());
            String logoUrl = objectStorageService.saveFileAlone((MultipartFile) logo, path);
            storeToEdit.setLogo(logoUrl);
        }
        if (storeImage != null) {
            objectStorageService.deleteFile(storeToEdit.getStoreImage());
            String storeImageUrl = objectStorageService.saveFileAlone((MultipartFile) storeImage, path);
            storeToEdit.setStoreImage(storeImageUrl);
        }
        storeToEdit.setName(store.getName());
        storeToEdit.setDescription(store.getDescription());
        return storeRepository.save(storeToEdit);
    }

    @Override
    public void deleteStoreById(Long id) {
        Store store = storeRepository.findById(id).get();
        if (store != null && store.getLogo() != null) {
            store.getProducts().forEach(
                    product -> objectStorageService.deleteFiles(product.getImageUrls()));
            objectStorageService.deleteFile(store.getLogo());
            objectStorageService.deleteFile(store.getStoreImage());

        }
        System.out.println(store.getVendor());
        if (store.getVendor() != null) {
            System.out.println("here");
            Vendor vendor = vendorRepository.findById(store.getVendor().getId()).get();
            vendor.getStores().remove(store);
            vendorRepository.save(vendor);
            store.setVendor(null);
            storeRepository.save(store);
            storeRepository.deleteById(id);
        }
        storeRepository.deleteById(id);
    }

    // @Override
    // public Store updateStore(Long id, Store store) {
    // Optional<Store> optionalStore = storeRepository.findById(id);

    // if (optionalStore.isPresent()) {
    // Store existingStore = optionalStore.get();
    // existingStore.setName(store.getName());
    // existingStore.setDescription(store.getDescription());
    // existingStore.setVendor(store.getVendor());
    // // existingStore.setProducts(store.getProducts());
    // existingStore.setStoreImage(store.getStoreImage());
    // existingStore.setLogo(store.getLogo());
    // existingStore.setApproved(store.isApproved());
    // return storeRepository.save(existingStore);
    // } else {
    // return null;
    // }
    // }

    public Store assignProductToStore(long id, Product product) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with ID: "));
        store.getProducts().add(product);
        return storeRepository.save(store);
    }

    public Store assignExistingProductToStore(long storeiId, long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with ID: "));
        ;
        Store store = storeRepository.findById(storeiId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with ID: "));
        store.getProducts().add(product);
        return storeRepository.save(store);
    }

    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }
}

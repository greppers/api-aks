package tn.esprit.pidev.Services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.pidev.Entities.Store;
import tn.esprit.pidev.Entities.User;
import tn.esprit.pidev.Entities.Vendor;
import tn.esprit.pidev.Repositories.StoreRepository;
import tn.esprit.pidev.Repositories.UserRepository;
import tn.esprit.pidev.Repositories.VendorRepository;

@Service
@Transactional
public class VendorService implements IVendorService {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Vendor> getAllVendors() {
        try {
            return (List<Vendor>) vendorRepository.findAll();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Vendor> getVendorById(Long id) {
        return vendorRepository.findVendorByUserId(id);
    }

    @Override
    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }
    @Override
    public Vendor saveVendorWithUser(Vendor vendor,String username) {
        User  user=userRepository.findByUsername(username);
        if (user!=null) {
            vendor.setUser(user);
        }
        return vendorRepository.save(vendor);
    }
    @Override
    public void saveVendorWithUser(User user){
        Vendor vendor=new Vendor();
        vendor.setName(user.getUsername());
        vendor.setLastName(user.getUsername());
        vendor.setUser(user);
        vendorRepository.save(vendor);
    }
    @Override
    public Vendor assignStoreToVendor(Long id, Store store) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with ID: "));
        store.setVendor(vendor);
        // storeRepository.save(store);
        vendor.getStores().add(store);
        return vendorRepository.save(vendor);

    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public Vendor updateVendor(Long id, Vendor vendor) {
        Optional<Vendor> optionalVendor = vendorRepository.findById(id);

        if (optionalVendor.isPresent()) {
            Vendor existingVendor = optionalVendor.get();
            existingVendor.setName(vendor.getName());
            existingVendor.setLastName(vendor.getLastName());
            existingVendor.setProfileImageLink(vendor.getProfileImageLink());
            existingVendor.setStores(vendor.getStores());
            return vendorRepository.save(existingVendor);
        } else {
            return null;
        }
    }
    public Store assignExistingStoreToVendor(long vendorId,long storeId) {
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found with ID: " + vendorId));
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new EntityNotFoundException("Store not found with ID: " + storeId));
        store.setVendor(vendor);
        vendor.addStore(store);
        return storeRepository.save(store);
    }
}
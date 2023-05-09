package tn.esprit.pidev.Services;

import java.util.List;
import java.util.Optional;

import tn.esprit.pidev.Entities.Store;
import tn.esprit.pidev.Entities.User;
import tn.esprit.pidev.Entities.Vendor;

public interface IVendorService {

    List<Vendor> getAllVendors();

    Optional<Vendor> getVendorById(Long id);

    Vendor saveVendor(Vendor vendor);
    void saveVendorWithUser(User user);


    void deleteVendorById(Long id);

    Vendor updateVendor(Long id, Vendor vendor);

    Vendor assignStoreToVendor(Long id, Store store);
    Store assignExistingStoreToVendor(long vendorId,long storeId);
    public Vendor saveVendorWithUser(Vendor vendor,String username);

}
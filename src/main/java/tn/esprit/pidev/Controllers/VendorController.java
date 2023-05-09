package tn.esprit.pidev.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.pidev.Entities.Store;
import tn.esprit.pidev.Entities.Vendor;
import tn.esprit.pidev.Services.IVendorService;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin
public class VendorController {

    @Autowired
    private IVendorService vendorService;

    @GetMapping("")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
        Optional<Vendor> vendor = vendorService.getVendorById(id) ;
        if (vendor.isPresent()) {
            return new ResponseEntity<>(vendor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
        Vendor savedVendor = vendorService.saveVendor(vendor);
        return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor) {
        Vendor updatedVendor = vendorService.updateVendor(id, vendor);
        if (updatedVendor != null) {
            return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/store/{id}")
    public ResponseEntity<Vendor> assignStoreToVendor(@PathVariable Long id, @RequestBody Store store) {
        Vendor updatedVendor = vendorService.assignStoreToVendor(id,store );
        if (updatedVendor != null) {
            return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteVendorById(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}/store/{storeId}")
    public ResponseEntity<HttpStatus> assignExistingStoreToVendor(@PathVariable Long id,@PathVariable Long storeId) {
        Store store= vendorService.assignExistingStoreToVendor(id, storeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

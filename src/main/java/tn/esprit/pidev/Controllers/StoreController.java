package tn.esprit.pidev.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.esprit.pidev.Entities.Store;
import tn.esprit.pidev.Entities.Vendor;
import tn.esprit.pidev.Services.IStoreService;

@RestController
@RequestMapping("/api/stores")
@CrossOrigin
public class StoreController {

    @Autowired
    private IStoreService storeService;

    @GetMapping
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Optional<Store> store = storeService.getStoreById(id);
        return store.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // @PostMapping("/{id}")
    // public Store createOrUpdateStore(@RequestBody Store store) {
    // return storeService.createOrUpdateStore(store);
    // }
    @PostMapping("/{id}")
    public Store createOrUpdateStore(@PathVariable("id") long id, @RequestParam("store") String storeJson,
            @RequestParam(value = "userId",required = false) String userId,
            @RequestParam(value = "logo", required = false) MultipartFile logo,
            @RequestParam(value = "banner", required = false) MultipartFile storeImage)
            throws JsonMappingException, JsonProcessingException {
        System.out.println("hello");
        ObjectMapper objectMapper = new ObjectMapper();
        Store store = objectMapper.readValue(storeJson, Store.class);
        Long longUserId=null;
        if(userId!=null)
        longUserId=objectMapper.readValue(userId, Long.class);
        return storeService.createOrUpdateStore(longUserId,store, logo, storeImage);
    }

    @PutMapping("/{id}")
    public Store UpdateStore(@PathVariable("id") long id, @RequestParam("store") String storeJson,
            @RequestParam(value = "logo", required = false) MultipartFile logo,
            @RequestParam(value = "banner", required = false) MultipartFile storeImage)
            throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Store store = objectMapper.readValue(storeJson, Store.class);
        return storeService.updateStore(id,store, logo, storeImage);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody
    // Store store) {
    // Store updatedStore = storeService.updateStore(id, store);
    // return updatedStore != null ? ResponseEntity.ok(updatedStore) :
    // ResponseEntity.notFound().build();
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreById(@PathVariable Long id) {
        storeService.deleteStoreById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/product/{id}/{productId}")
    public ResponseEntity<Store> assignProductToStore(@PathVariable Long id, @PathVariable Long productId) {
        Store updatedStore = storeService.assignExistingProductToStore(id, productId);
        return updatedStore != null ? ResponseEntity.ok(updatedStore) : ResponseEntity.notFound().build();
    }
}

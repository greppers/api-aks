package tn.esprit.pidev.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.pidev.Entities.Vendor;

public interface VendorRepository extends JpaRepository<Vendor,Long>{
    Optional<Vendor> findVendorByUserId(Long userId);
}

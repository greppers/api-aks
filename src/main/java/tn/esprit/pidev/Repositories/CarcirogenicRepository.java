package tn.esprit.pidev.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.pidev.Entities.Carcirogenic;

public interface CarcirogenicRepository extends JpaRepository<Carcirogenic, Long> {
    Carcirogenic findByName(String name);

}
package tn.esprit.pidev.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.pidev.Entities.Composition;

public interface CompositionRepository extends JpaRepository<Composition,Long> {
}

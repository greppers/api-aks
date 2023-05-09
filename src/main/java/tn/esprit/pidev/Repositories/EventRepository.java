package tn.esprit.pidev.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.pidev.Entities.Event;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByTitre(String titre);
    
    List<Event> findByUserid(int userid);

}

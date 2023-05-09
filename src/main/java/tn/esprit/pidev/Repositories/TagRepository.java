package tn.esprit.pidev.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.pidev.Entities.Tag;

public interface TagRepository extends JpaRepository<Tag,Long>{
    Tag findFirstByName(String name);
    Tag findByName(String name);
}

package tn.esprit.pidev.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.Entities.Role;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Role,Long> {
}

package tn.esprit.pidev.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.Entities.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public User findByemail(String email);
    public User findByUsername(String username);

    public boolean existsByEmail(String email);

    @Query("select u.active from User u where u.email=?1")
    public int getActive(String email);

    @Query("select u.password from User u where u.email=?1")
    public String getPasswordByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.userRoles r WHERE r.roleName = 'Marchant'")
    List<User> findAllSellers();
    @Query("SELECT u FROM User u JOIN u.userRoles r WHERE r.roleName = 'Client'")
    List<User> findAllClients();

    @Query("SELECT count(*) FROM User u JOIN u.userRoles r WHERE r.roleName = 'Client'")
    Long CountClients();

    @Query("SELECT count(*) FROM User u JOIN u.userRoles r WHERE r.roleName = 'Admin'")
    Long CountAdmins();
    @Query("SELECT count(*) FROM User u JOIN u.userRoles r WHERE r.roleName = 'Marchant'")
    Long CountMarchants();
}

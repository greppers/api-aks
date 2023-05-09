package tn.esprit.pidev.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.pidev.Entities.User;
import tn.esprit.pidev.Payload.UserPrincipal;
import tn.esprit.pidev.Repositories.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByemail(email);
        System.out.println(user.getEmail() + "         " +user.getPassword());
        UserPrincipal userPrincipal = new UserPrincipal(user);
        return userPrincipal;
    }

    @Transactional
    public void addUser(User user){
        userRepository.save(user);
    }

    public boolean ifEmailExist(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public int getUserActive(String email){
        return userRepository.getActive(email);
    }

    @Transactional
    public String getPasswordByEmail(String email){
        return userRepository.getPasswordByEmail(email);
    }

    public User getUserByMail(String mail){
        return this.userRepository.findByemail(mail);
    }
    public void editUser(User user){

        this.userRepository.save(user);
    }

    public User updateUser(User seller){
        Long id = seller.getId();
        User us=userRepository.findById(id).get();
        us.setUsername(seller.getUsername());
        us.setEmail(seller.getEmail());
        us.setPhone_number(seller.getPhone_number());
        return userRepository.save(us);
    }

    public List<User> fetchSellerList() {
        try {
            return userRepository.findAllSellers();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error fetching users: " + ex.getMessage(), ex);
        }
    }

    public List<User> fetchClientList() {
        try {
            return userRepository.findAllClients();
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error fetching users: " + ex.getMessage(), ex);
        }
    }

    public void deleteSeller(Long id){
        userRepository.deleteById(id);
    }


}

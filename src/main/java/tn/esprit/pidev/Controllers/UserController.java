package tn.esprit.pidev.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.Entities.Code;
import tn.esprit.pidev.Entities.Role;
import tn.esprit.pidev.Entities.User;
import tn.esprit.pidev.Payload.*;
import tn.esprit.pidev.Repositories.UserRepository;
import tn.esprit.pidev.Services.*;

import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping
public class UserController {

    private TokenService tokenService;
    private UserService userService;
    private AuthoritiesService authoritiesService;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private UserRepository userRepository;

    @Autowired
    public UserController( UserRepository userRepository,TokenService tokenService, UserService userService, AuthoritiesService authoritiesService, PasswordEncoder passwordEncoder, EmailService emailService,PasswordEncoder encoder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.authoritiesService = authoritiesService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userRepository = userRepository;

    }
    @Autowired
    public IVendorService vendorService;

    @PostMapping("/signin")
    public LoginResponse logIn(@RequestBody JwtLogin jwtLogin){
        return tokenService.login(jwtLogin);
    }
    
    @GetMapping("/getSellers")
    public List<User> GetAllSellers(){
        return userService.fetchSellerList();
    }

    @GetMapping("/getClients")
    public List<User> GetAllClients(){
        return userService.fetchClientList();
    }

    // http://localhost:8080/sigup
    @PostMapping("/signup")
    public AccountResponse createUser(@RequestBody JwtSignup jwtLogin){
        AccountResponse accountResponse = new AccountResponse();
        boolean result = userService.ifEmailExist(jwtLogin.getEmail());
        if(result){
            accountResponse.setResult(0);
        } else {
            String myCode = UserCode.getCode();
            User user = new User();
            user.setUsername(jwtLogin.getUsername());
            user.setEmail(jwtLogin.getEmail());
            user.setPassword(passwordEncoder.encode(jwtLogin.getPassword()));
            user.setPhone_number(jwtLogin.getPhone_number());
            user.setUserRoles(jwtLogin.getRoles().stream().map(r -> {
                Role ur = new Role();
                ur.setRoleName(r);
                return ur;
            }).collect(Collectors.toSet()));
            user.setActive(0);
            Mail mail = new Mail(jwtLogin.getEmail(),myCode);
            emailService.sendCodeByMail(mail);
            Code code = new Code();
            code.setCode(myCode);
            user.setCode(code);
            User createdUser= userRepository.save(user);
            vendorService.saveVendorWithUser(createdUser);
            accountResponse.setResult(1);
        }
        return accountResponse;
    }


    // http://localhost:8080/active
    @PostMapping("/active")
    public UserActive getActiveUser(@RequestBody JwtLogin jwtLogin){
        String enPassword = userService.getPasswordByEmail(jwtLogin.getEmail());  // from DB
        boolean result = passwordEncoder.matches(jwtLogin.getPassword(),enPassword); // Sure
        UserActive userActive = new UserActive();
        if (result){
            int act = userService.getUserActive(jwtLogin.getEmail());
            if(act == 0){
                String code = UserCode.getCode();
                Mail mail = new Mail(jwtLogin.getEmail(),code);
                emailService.sendCodeByMail(mail);
                User user = userService.getUserByMail(jwtLogin.getEmail());
                user.getCode().setCode(code);
                userService.editUser(user);
            }
            userActive.setActive(act);
        } else {
            userActive.setActive(-1);
        }
        return userActive;
    }

    // http://localhost:8080/activated
    @PostMapping("/activated")
    public AccountResponse activeAccount(@RequestBody ActiveAccount activeAccount){
        User user = userService.getUserByMail(activeAccount.getMail());
        AccountResponse accountResponse = new AccountResponse();
        if(user.getCode().getCode().equals(activeAccount.getCode())){
            user.setActive(1);
            userService.editUser(user);
            accountResponse.setResult(1);
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }


    // http://localhost:8080/checkEmail
    @PostMapping("/checkEmail")
    public AccountResponse resetPasswordEmail(@RequestBody ResetPassword resetPassword){
        User user = this.userService.getUserByMail(resetPassword.getEmail());
        AccountResponse accountResponse = new AccountResponse();
        if(user != null){
            String code = UserCode.getCode();
            Mail mail = new Mail(resetPassword.getEmail(),code);
           // emailService.sendCodeByMail(mail);
            user.getCode().setCode(code);
            this.userService.editUser(user);
            accountResponse.setResult(1);
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }

    // http://localhost:8080/resetPassword
    @PostMapping("/resetPassword")
    public AccountResponse resetPassword(@RequestBody NewPassword newPassword){
        User user = this.userService.getUserByMail(newPassword.getEmail());
        AccountResponse accountResponse = new AccountResponse();
        if(user != null){
            if(user.getCode().equals(newPassword.getCode())){
                user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
                userService.addUser(user);
                accountResponse.setResult(1);
            } else {

                accountResponse.setResult(0);
            }
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }

    @PutMapping("/updateSellers")
    @CrossOrigin("http://localhost:4200")

    public User updateUser(@RequestBody User seller){
        return userService.updateUser(seller);
    }

    @DeleteMapping("/deleteSellers")
    @CrossOrigin("http://localhost:4200")
    public void deleteSellers(@RequestParam Long id){
         userService.deleteSeller(id);
    }


    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
        Long totalUsers = userRepository.count();
        Long totalClients = userRepository.CountClients();
        Long totalMarchants = userRepository.CountMarchants();
        long totalAdmins = userRepository.CountAdmins();
        Map<String,Object> data = new HashMap<>();
        data.put("totalUsers",totalUsers);
        data.put("totalClients",totalClients);
        data.put("totalMarchants",totalMarchants);
        data.put("totalAdmins",totalAdmins);
       return ResponseEntity.ok(data);
    }

}

package tn.esprit.pidev.Services;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.Entities.User;
import tn.esprit.pidev.Payload.JwtLogin;
import tn.esprit.pidev.Payload.JwtProperties;
import tn.esprit.pidev.Payload.LoginResponse;
import tn.esprit.pidev.Payload.UserPrincipal;
import tn.esprit.pidev.Repositories.UserRepository;

import java.util.Date;

import javax.transaction.Transactional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
@Transactional
public class TokenService {
    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    @Autowired
    public TokenService(UserRepository userRepository,AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userRepository=userRepository;
    }


    private String generateToken(Authentication authResult) {

        // Grab principal
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
        System.out.println(principal.getUsername());

        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
        return token;
    }

    public LoginResponse login(JwtLogin jwtLogin) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtLogin.getEmail(),
                jwtLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        User user = userRepository.findByemail(jwtLogin.getEmail());
        String token = generateToken(authenticate);
        return new LoginResponse(user,jwtLogin.getEmail(),token);
    }
}

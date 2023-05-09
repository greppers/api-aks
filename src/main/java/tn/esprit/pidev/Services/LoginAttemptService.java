package tn.esprit.pidev.Services;

import org.springframework.stereotype.Service;
import tn.esprit.pidev.Entities.LoginAttempt;
import tn.esprit.pidev.Repositories.LoginAttemptRepository;

import java.time.LocalDateTime;

@Service
public class LoginAttemptService {

    private LoginAttemptRepository loginAttemptRepository;

    public LoginAttemptService(LoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    public void save(LoginAttempt loginAttempt) {
        loginAttemptRepository.save(loginAttempt);
    }

    public int getFailedLoginAttempts(String username) {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        return loginAttemptRepository.countByUsernameAndTimestampAfterAndSuccessIsFalse(username, tenMinutesAgo);
    }
}
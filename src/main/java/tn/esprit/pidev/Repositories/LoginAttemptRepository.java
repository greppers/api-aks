package tn.esprit.pidev.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.Entities.LoginAttempt;

import java.time.LocalDateTime;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    int countByUsernameAndTimestampAfterAndSuccessIsFalse(String username, LocalDateTime timestamp);

}
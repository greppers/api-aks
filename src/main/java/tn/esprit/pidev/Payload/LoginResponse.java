package tn.esprit.pidev.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.pidev.Entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private User user ;
    private String email;
    private String token;


}

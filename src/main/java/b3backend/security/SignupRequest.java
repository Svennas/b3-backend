package b3backend.security;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}
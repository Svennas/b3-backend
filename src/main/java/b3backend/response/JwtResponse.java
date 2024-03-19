package b3backend.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type;
    private int id;
    private String firstName;
    private String lastName;
    private String userName;

    public JwtResponse(String token, int id, String firstName, String lastName, String userName) {
        this.token = token;
        this.type  = "Bearer";
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }
}
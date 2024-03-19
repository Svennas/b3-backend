package b3backend.security;


import b3backend.response.JwtResponse;
import b3backend.response.MessageResponse;
import b3backend.security.jwt.JwtUtils;
import b3backend.user.User;
import b3backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)    //csrf
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {  //username och password kommer in som ett json objekt
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = this.jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {   // kommer in ett json objekt enligt modellen SignupRequest
        if (this.userRepository.existsByUserName(signupRequest.getUserName())) {    //om user redan finns i databasen
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken."));
        }

        User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getUserName(),
                this.encoder.encode(signupRequest.getPassword()));  //inte spara lösenord i plain text

        //Spara ny user i databas
        this.userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}
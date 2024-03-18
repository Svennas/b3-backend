package b3backend.user;

import b3backend.program.Program;
import b3backend.program.ProgramRepository;
import b3backend.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProgramRepository programRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User newUser = this.userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.set(newUser);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers() {
        List<User> allUsers = this.userRepository.findAll();

        UserListResponse customerListResponse = new UserListResponse();
        customerListResponse.set(allUsers);

        return ResponseEntity.ok(customerListResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.set(user);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}

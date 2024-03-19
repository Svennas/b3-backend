package b3backend.privateexercise;

import b3backend.response.*;
import b3backend.user.User;
import b3backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/privateexercises")
public class PrivateExerciseController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivateExerciseRepository privateExerciseRepository;

    // Get all private exercises
    @GetMapping
    public ResponseEntity<?> getAllPrivateExercises(@PathVariable int userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Privateexercise> allExercises = user.getPrivateExercises();

        ExerciseListResponse exerciseListResponse = new ExerciseListResponse();
        exerciseListResponse.set(allExercises);

        return ResponseEntity.ok(exerciseListResponse);
    }

    @PostMapping
    public ResponseEntity<?> createExercise(@RequestBody Privateexercise exercise, @PathVariable int userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        //spara userId i exercise
        exercise.setUser(user);

        Privateexercise newExercise = this.privateExerciseRepository.save(exercise);

        ExerciseResponse exerciseResponse = new ExerciseResponse();
        exerciseResponse.set(newExercise);

        return new ResponseEntity<>(exerciseResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{privateexerciseId}")
    public ResponseEntity<?> getExerciseById(@PathVariable int userId, @PathVariable int privateexerciseId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Privateexercise> allExercises = user.getPrivateExercises();

        Privateexercise privateexercise = allExercises.stream()
                .filter(p -> p.getId() == privateexerciseId)
                .findFirst()
                .orElse(null);

        if (privateexercise == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No private exercise with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ExerciseResponse exerciseResponse = new ExerciseResponse();
        exerciseResponse.set(privateexercise);

        return new ResponseEntity<>(exerciseResponse, HttpStatus.OK);
    }
}

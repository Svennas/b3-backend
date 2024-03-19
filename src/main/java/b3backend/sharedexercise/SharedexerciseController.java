package b3backend.sharedexercise;

import b3backend.response.ErrorResponse;
import b3backend.response.SharedexerciseListResponse;
import b3backend.response.SharedexerciseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sharedexercises")
public class SharedexerciseController {
    @Autowired
    SharedexerciseRepository sharedexerciseRepository;

    @GetMapping
    public ResponseEntity<?> getAllSharedExercises() {
        List<Sharedexercise> allExercises = sharedexerciseRepository.findAll();

        SharedexerciseListResponse sharedexerciseListResponse = new SharedexerciseListResponse();
        sharedexerciseListResponse.set(allExercises);

        return ResponseEntity.ok(sharedexerciseListResponse);
    }

    @GetMapping("/{sharedexerciseId}")
    public ResponseEntity<?> getSharedexerciseById(@PathVariable int sharedexerciseId) {
        List<Sharedexercise> allSharedExercises = this.sharedexerciseRepository.findAll();

        Sharedexercise sharedexercise = allSharedExercises.stream()
                .filter(exercise -> exercise.getId() == sharedexerciseId)
                .findFirst()
                .orElse(null);

        if (sharedexercise == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No shared exercise with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        SharedexerciseResponse sharedexerciseResponse = new SharedexerciseResponse();
        sharedexerciseResponse.set(sharedexercise);

        return new ResponseEntity<>(sharedexerciseResponse, HttpStatus.OK);
    }
}

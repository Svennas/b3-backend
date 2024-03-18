package b3backend.programexercise;

import b3backend.program.Program;
import b3backend.program.ProgramRepository;
import b3backend.response.*;
import b3backend.user.User;
import b3backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/programs/{programId}/programexercises")
public class ProgramexerciseController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private  ProgramexerciseRepository programexerciseRepository;

    @PostMapping
    public ResponseEntity<?> createProgramexercise(@RequestBody Programexercise programexercise, @PathVariable int userId, @PathVariable int programId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Program> allPrograms = user.getPrograms();

        Program program = allPrograms.stream()
                .filter(p -> p.getId() == programId)
                .findFirst()
                .orElse(null);

        //set vilket program det tillhör
        programexercise.setProgram(program);

        //spara i databas
        Programexercise newProgramexercise = this.programexerciseRepository.save(programexercise);

        ProgramexerciseResponse programexerciseResponse = new ProgramexerciseResponse();
        programexerciseResponse.set(newProgramexercise);

        return new ResponseEntity<>(programexerciseResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllProgramexercises(@PathVariable int userId, @PathVariable int programId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Program> allPrograms = user.getPrograms();

        Program program = allPrograms.stream()
                .filter(p -> p.getId() == programId)
                .findFirst()
                .orElse(null);

        List<Programexercise> allProgramExercises = program.getProgramExercises();

        ProgramexerciseListResponse programexerciseListResponse = new ProgramexerciseListResponse();
        programexerciseListResponse.set(allProgramExercises);

        return new ResponseEntity<>(programexerciseListResponse, HttpStatus.OK);
    }

    @GetMapping("/{programexerciseId}")
    public ResponseEntity<?> getProgramexerciseById(@PathVariable int userId, @PathVariable int programId, @PathVariable int programexerciseId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Program> allPrograms = user.getPrograms();

        Program program = allPrograms.stream()
                .filter(p -> p.getId() == programId)
                .findFirst()
                .orElse(null);

        if (program == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No program with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Programexercise> allProgramExercises = program.getProgramExercises();

        Programexercise programexercise = allProgramExercises.stream()
                .filter(p -> p.getId() == programexerciseId)
                .findFirst()
                .orElse(null);

        if (programexercise == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No program exercise with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ProgramexerciseResponse programexerciseResponse = new ProgramexerciseResponse();
        programexerciseResponse.set(programexercise);

        return new ResponseEntity<>(programexerciseResponse, HttpStatus.OK);
    }

    @PutMapping("/{programexerciseId}")
    public ResponseEntity<?> updateProgramexercise(@PathVariable int userId, @PathVariable int programId, @PathVariable int programexerciseId, @RequestBody Programexercise programexercise) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Program> allPrograms = user.getPrograms();

        Program program = allPrograms.stream()
                .filter(p -> p.getId() == programId)
                .findFirst()
                .orElse(null);

        if (program == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No program with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Programexercise> allProgramExercises = program.getProgramExercises();

        Programexercise programexerciseToBeUpdated = allProgramExercises.stream()
                .filter(p -> p.getId() == programexerciseId)
                .findFirst()
                .orElse(null);

        if (programexercise == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No program exercise with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        programexerciseToBeUpdated.setTitle(programexercise.getTitle());
        programexerciseToBeUpdated.setDescription(programexercise.getDescription());
        programexerciseToBeUpdated.setSets(programexercise.getSets());
        programexerciseToBeUpdated.setReps(programexercise.getReps());

        Programexercise updatedProgramexercise = this.programexerciseRepository.save(programexerciseToBeUpdated);

        ProgramexerciseResponse programexerciseResponse = new ProgramexerciseResponse();
        programexerciseResponse.set(updatedProgramexercise);

        return new ResponseEntity<>(programexerciseResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<?> deleteProgram(@PathVariable int userId, @PathVariable int programId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Program> allPrograms = user.getPrograms();

        Program programToBeDeleted = allPrograms.stream()
                .filter(p -> p.getId() == programId)
                .findFirst()
                .orElse(null);

        if (programToBeDeleted == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No program with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.programRepository.delete(programToBeDeleted);

        ProgramResponse programResponse = new ProgramResponse();
        programResponse.set(programToBeDeleted);

        return new ResponseEntity<>(programResponse, HttpStatus.CREATED);
    }
}

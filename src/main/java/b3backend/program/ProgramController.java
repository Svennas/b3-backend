package b3backend.program;

import b3backend.programexercise.Programexercise;
import b3backend.programexercise.ProgramexerciseRepository;
import b3backend.response.ErrorResponse;
import b3backend.response.ProgramListResponse;
import b3backend.response.ProgramResponse;
import b3backend.user.User;
import b3backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)    //csrf
@RestController
@RequestMapping("/users/{userId}/programs")
public class ProgramController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private ProgramexerciseRepository programexerciseRepository;


    @PostMapping
    public ResponseEntity<?> createProgram(@RequestBody Program program, @PathVariable int userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (program.getProgramExercises() == null) {
            program.setProgramExercises(new ArrayList<>());
        }

        program.setUser(user);

        //skapa nytt program i databas
        Program newProgram = this.programRepository.save(program);

        /*spara program id för program exercise*/
        for (Programexercise exercise : newProgram.getProgramExercises()) {
            System.out.println(exercise);
            Programexercise newProgramExercise = new Programexercise();
            newProgramExercise.setTitle(exercise.getTitle());
            newProgramExercise.setDescription(exercise.getDescription());
            newProgramExercise.setSets(exercise.getSets());
            newProgramExercise.setReps(exercise.getReps());
            newProgramExercise.setProgram(newProgram);
            this.programexerciseRepository.save(newProgramExercise);
        }

        ProgramResponse programResponse = new ProgramResponse();
        programResponse.set(newProgram);

        return new ResponseEntity<>(programResponse, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<?> getAllPrograms(@PathVariable int userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Program> allPrograms = user.getPrograms();

        ProgramListResponse programListResponse = new ProgramListResponse();
        programListResponse.set(allPrograms);

        return new ResponseEntity<>(programListResponse, HttpStatus.OK);
    }

    @GetMapping("/{programId}")
    public ResponseEntity<?> getProgramById(@PathVariable int userId, @PathVariable int programId) {
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

        ProgramResponse programResponse = new ProgramResponse();
        programResponse.set(program);

        return new ResponseEntity<>(programResponse, HttpStatus.OK);
    }

    @PutMapping("/{programId}")
    public ResponseEntity<?> updateProgram(@PathVariable int userId, @PathVariable int programId, @RequestBody Program program) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Program> allPrograms = user.getPrograms();

        Program programToBeUpdated = allPrograms.stream()
                .filter(p -> p.getId() == programId)
                .findFirst()
                .orElse(null);

        if (programToBeUpdated == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No program with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        //ta bort gamla program exercises ur databas
        Iterator<Programexercise> iterator = programToBeUpdated.getProgramExercises().iterator();
        while (iterator.hasNext()) {
            Programexercise exerciseToDelete = iterator.next();
            for (Programexercise programexercise : this.programexerciseRepository.findAll()) {
                if (programexercise.getId() == exerciseToDelete.getId()) {
                    this.programexerciseRepository.delete(programexercise);
                    break;
                }
            }
            iterator.remove();
        }

        programToBeUpdated.setTitle(program.getTitle());
        programToBeUpdated.setProgramExercises(program.getProgramExercises());

        //skapa nya program exercises och lägg in i databas
        for (Programexercise exercise : programToBeUpdated.getProgramExercises()) {
            System.out.println(exercise);
            Programexercise newProgramExercise = new Programexercise();
            newProgramExercise.setTitle(exercise.getTitle());
            newProgramExercise.setDescription(exercise.getDescription());
            newProgramExercise.setSets(exercise.getSets());
            newProgramExercise.setReps(exercise.getReps());
            newProgramExercise.setProgram(programToBeUpdated);
            this.programexerciseRepository.save(newProgramExercise);
        }

        Program updatedProgram = this.programRepository.save(programToBeUpdated);

        ProgramResponse programResponse = new ProgramResponse();
        programResponse.set(updatedProgram);

        return new ResponseEntity<>(programResponse, HttpStatus.CREATED);
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

        for (Programexercise exerciseToDelete : programToBeDeleted.getProgramExercises()) {
            for (Programexercise programexercise : this.programexerciseRepository.findAll()) {
                if (programexercise.getId() == exerciseToDelete.getId()) {
                    this.programexerciseRepository.delete(programexercise);
                }
            }
            programToBeDeleted.getProgramExercises().remove(exerciseToDelete);
        }

        this.programRepository.delete(programToBeDeleted);

        ProgramResponse programResponse = new ProgramResponse();
        programResponse.set(programToBeDeleted);

        return new ResponseEntity<>(programResponse, HttpStatus.CREATED);
    }
}

package b3backend.user;

import b3backend.exercise.Exercise;
import b3backend.program.Program;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String userName;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Program> programs;

    /*
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Exercise> privateExercises;

    @OneToMany(mappedBy = "shared_by")  //ManyToMany?
    @JsonIgnoreProperties("user")
    private List<Exercise> sharedExercises;
     */

    public User(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }
}
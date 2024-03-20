package b3backend.program;

import b3backend.programexercise.Programexercise;
import b3backend.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @ManyToOne  //ett program har endast en user. En user kan ha många program
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore //ta bort user details när man fetchar ett program by id
    private User user;

    @OneToMany(mappedBy = "program")   //ett program har många program exercises.
    @JsonIgnoreProperties("program")
    private List<Programexercise> programExercises;

    public Program(String title) {
        this.title = title;
    }
}

package b3backend.programexercise;

import b3backend.program.Program;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "programexercises")
public class Programexercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private int sets;

    @Column
    private int reps;

    @ManyToOne  //ett program exercise tillhör endast ett program. Ett program kan ha många program exercises
    @JoinColumn(name = "program_id", nullable = false)
    @JsonIgnore //ta bort när man fetchar ett program exercise by id
    private Program program;

    public Programexercise(String title, String description, int sets, int reps) {
        this.title = title;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
    }
}

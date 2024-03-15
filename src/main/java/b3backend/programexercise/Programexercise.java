package b3backend.programexercise;

import b3backend.program.Program;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "programsexercises")
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

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private OffsetDateTime updatedAt;

    @ManyToOne  //en program exercise tillhör endast ett program. Ett program kan ha många program exercises
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @PrePersist
    public void onCreate() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        this.createdAt = currentTime;
        this.updatedAt = currentTime;
    }

    @PreUpdate
    public void onUpdate() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        this.updatedAt = currentTime;
    }

    public Programexercise(String title, String description, int sets, int reps) {
        this.title = title;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
    }
}

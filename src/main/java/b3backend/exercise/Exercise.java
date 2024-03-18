package b3backend.exercise;

import b3backend.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    /* shared exercises
    @ManyToMany // ManyToMany relation behöver ett extra table i databas
    @JoinColumn(name = "shared_by_id")
    private User sharedBy;
    */

    // privata exercises
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }
}

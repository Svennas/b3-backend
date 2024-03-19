package b3backend.sharedexercise;

import b3backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sharedexercises")
public class Sharedexercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    //@ManyToMany(mappedBy = "sharedExercises")
    //private List<User> users;

    public Sharedexercise(String title, String description) {
        this.title = title;
        this.description = description;
    }
}

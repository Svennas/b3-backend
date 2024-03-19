package b3backend.privateexercise;

import b3backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "privateexercises")
public class Privateexercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore //ta bort när man fetchar ett program exercise by id
    private User user;

    public Privateexercise(String title, String description) {
        this.title = title;
        this.description = description;
    }
}

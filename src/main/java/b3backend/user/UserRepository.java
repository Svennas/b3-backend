package b3backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //tillagt från java security day 2
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);   //"Username" måste matcha fält i modellen. spring skapar själv denna metod. optional = user may not exist

    Boolean existsByUserName(String userName);
}

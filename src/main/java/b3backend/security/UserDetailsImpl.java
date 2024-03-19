package b3backend.security;

import b3backend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    //these are annotated elsewhere
    private int id;
    private String firstName;
    private String lastName;
    private String userName;

    @JsonIgnore //don't return password for a user in the json
    private String password;

    //vi vet inte vilken klass än, men den extendar iallafall GrantedAuthority
    //private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(int id,
                           String firstName,
                           String lastName,
                           String userName,
                           String password)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    //går igenom alla roller associerade med en user. mappar dom och skapar en lista av dom
    //för dom ska användas för att generera ett objekt som vi ska returnera
    //denna metod kan kallas innan konstruktorn eftersom den är static
    //den skapar saker som behövs för att kalla konstruktorn
    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getPassword()
        );
    }

    @Override   //finns bara för att kunna använda interfacet UserDetails
    public Collection<? extends GrantedAuthority> getAuthorities() {    //bör returnera alla authorities
        return null;
    }

    //@Override   //finns bara för att kunna använda interfacet UserDetails
    public String getUsername() {
        return this.userName;
    }

    //dessa fyra overridade metoder finns bara för att kunna använda interfacet UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        //är o ett objekt av denna klass så kan man casta o till denna klass
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
package b3backend.security;


import b3backend.security.jwt.AuthEntryPointJwt;
import b3backend.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorisedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userDetailsService);
        authProvider.setPasswordEncoder(this.passwordEncoder());

        return authProvider;
    }

    @Bean   //används i AuthController
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //there is no separate login page
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable()) // ska tas bort innan produktion
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(this.unauthorisedHandler))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Stateless = doesn't depend on previous things
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/auth/**").permitAll()   //anyone can sign in and sign up. ** means zero or more folders within this directory
                        .anyRequest().authenticated()   //authenticate for any other request
                );

        http.authenticationProvider(this.authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);  //check if the token is there and valid, then it's good to go

        return http.build();
    }
}


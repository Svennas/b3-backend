package b3backend.security.jwt;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component  //ungefär som bean. säger åt spring att generera denna klass vid körning
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class); //Logger importerad från slf4j

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorised error: {}", authException.getMessage()); //If someone is unauthorized or login doesnt work
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorised"); //Ett 401 error som skickas till användaren
    }
}
package b3backend.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {  //extendar ej Response, lite special responsis
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
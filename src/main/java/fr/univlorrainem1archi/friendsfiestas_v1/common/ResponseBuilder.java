package fr.univlorrainem1archi.friendsfiestas_v1.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

public class ResponseBuilder {
    private final HttpStatus status;
    private final String message;
    private String dataName;
    private final Object data;

    public ResponseBuilder(HttpStatus status, String message, String dataName, Object data) {
        this.status = status;
        this.message = message;
        this.dataName = dataName;
        this.data = data;
    }

    public ResponseEntity<Response> buildResponse(){

       return ResponseEntity.ok(
               Response.builder()
                       .timestamp(LocalDateTime.now())
                       .httpStatus(status)
                       .httpStatusCode(status.value())
                       .message(message)
                       .data(Map.of(dataName,data))
                       .build()
       );
   }
}

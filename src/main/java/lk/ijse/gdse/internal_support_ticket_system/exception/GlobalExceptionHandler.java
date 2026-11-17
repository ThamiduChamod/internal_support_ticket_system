//package lk.ijse.gdse.internal_support_ticket_system.exception;
//
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.Map;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity> handleNotFound(EntityNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(Map.of("error", ex.getMessage()));
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity> handleBadRequest(IllegalArgumentException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(Map.of("error", ex.getMessage()));
//    }
//}
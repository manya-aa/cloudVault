package com.project.cloudInventory.exception;

import com.project.cloudInventory.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        false,
                        "Something went wrong",
                        null
                ));
    }
//@ExceptionHandler(Exception.class)
//public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
//
//    ex.printStackTrace();
//
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//            .body(new ApiResponse<>(
//                    false,
//                    ex.getMessage(),
//                    null
//            ));
}

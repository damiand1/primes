package com.dyl.prime.web.exception;

import com.dyl.prime.model.exception.AlgorithmNotSupportedException;
import com.dyl.prime.model.exception.InvalidPrimeNumberRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> handleException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        methodArgumentTypeMismatchException.printStackTrace();
        // Unfortunately spring wraps our custom exception for invalid algorithm into MethodArgumentTypeMismatchException
        // For this reason we look into root cause to see if there is an AlgorithmNotSupportedException as top cause.
        Throwable cause = methodArgumentTypeMismatchException.getCause();
        while(methodArgumentTypeMismatchException.getCause() != null) {
            if(cause instanceof AlgorithmNotSupportedException) {
                return new ResponseEntity<>("Specified algorithm is not supported", HttpStatus.BAD_REQUEST);
            }
            cause = cause.getCause();
        }
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidPrimeNumberRangeException.class})
    public ResponseEntity<String> handleException(InvalidPrimeNumberRangeException  invalidPrimeNumberRangeException) {
        invalidPrimeNumberRangeException.printStackTrace();
        System.err.println(invalidPrimeNumberRangeException);
        return new ResponseEntity<>("Invalid range specified. Prime numbers can only be positive integers", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception  exception) {
        exception.printStackTrace();
        return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

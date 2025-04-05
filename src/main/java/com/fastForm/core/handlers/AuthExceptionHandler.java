package com.fastForm.core.handlers;

import com.fastForm.core.Dto.ResponseDto;
import com.fastForm.core.exceptions.auth.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ResponseDto> handleAuthException(AuthException ex) {
        ResponseDto responseDto = new ResponseDto(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatusByType()).body(responseDto);
    }

}

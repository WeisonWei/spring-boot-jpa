package com.weison.sbj.config;

import com.weison.sbj.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    ResponseEntity<UserException> handleControllerException(HttpServletResponse response, UserException ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();
        ResponseEntity<UserException> body = bodyBuilder.body(ex);
        log.error("User Service Exception: Code[{}] Message[{}]", ex.getCode(), ex.getMessage());
        return body;
    }

}
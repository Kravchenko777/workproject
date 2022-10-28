package com.max.controllers;

import com.max.exceptions.MyExeption1;
import com.max.exceptions.MyExeption2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AdviceControllerTest extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MyExeption1.class)
    public ResponseEntity<String> handleMyExeption1(MyExeption1 e, WebRequest request){
        return new ResponseEntity<>("как то так 1" + e.getMessage() +" " + request.getRemoteUser(), HttpStatus.OK);
    }





    @ExceptionHandler(MyExeption2.class)
    public ResponseEntity<String> handleMyExeption2(MyExeption2 e){
        return new ResponseEntity<>("как то так 2", HttpStatus.OK);
    }
}

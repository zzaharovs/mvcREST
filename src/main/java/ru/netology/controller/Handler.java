package ru.netology.controller;


//import com.google.gson.JsonSyntaxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.netology.exception.NotFoundException;

@ControllerAdvice
public class Handler {

    //Сделал как в задании, но мне больше нравится через ResponseEntity
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}

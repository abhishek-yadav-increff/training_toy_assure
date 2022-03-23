package com.increff.channel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.MessageData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageData handle(ApiException e) {
        MessageData data = new MessageData();
        data.setMessage(e.getMessage());
        data.setErrorDatas(e.getErrorDatas());
        return data;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageData handle(Throwable e) {
        MessageData data = new MessageData();
        data.setMessage("An unknown error has occurred - " + e.getMessage());
        return data;
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageData handle(HttpStatusCodeException ex) {
        MessageData data = new MessageData();
        ObjectMapper mapper = new ObjectMapper();
        System.out.print(ex.getResponseBodyAsString());
        try {
            MessageData obj = mapper.readValue(ex.getResponseBodyAsString(), MessageData.class);
            return obj;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            data.setMessage("Error parsing response!!");
        }
        return data;
    }
}

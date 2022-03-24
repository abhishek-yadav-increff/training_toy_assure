package com.increff.commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.commons.model.ApiException;
import com.increff.commons.model.MessageData;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * ApiExceptionHelper
 */
public class ApiExceptionHelper {

    public static ApiException handleHttpException(HttpStatusCodeException ex) throws ApiException {
        ObjectMapper mapper = new ObjectMapper();
        MessageData obj = new MessageData();
        try {
            obj = mapper.readValue(ex.getResponseBodyAsString(), MessageData.class);
        } catch (Exception e) {
            return new ApiException("Error connecting to client!!");
        }
        return new ApiException(obj.getMessage(), obj.getErrorDatas());
    }
}

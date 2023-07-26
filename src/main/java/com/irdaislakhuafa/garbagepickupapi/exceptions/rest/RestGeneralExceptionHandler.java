package com.irdaislakhuafa.garbagepickupapi.exceptions.rest;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestGeneralExceptionHandler {
    @ExceptionHandler(value = {HttpMessageConversionException.class})
    public ResponseEntity<?> invalidRequestFormat(HttpMessageConversionException e) {
        final var errors = new HashMap<String, Object>() {{
            put("error", "your request body use invalid format, " + e.getMessage());
        }};

        final var body = RestResponse.<Object, Map<String, Object>>builder()
                .data(null)
                .errors(errors)
                .build();

        final var result = ResponseEntity.badRequest().body(body);
        return result;
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<?> badRequest(BadRequestException e) {
        final var errors = new HashMap<String, Object>() {{
            put("error", e.getMessage());
        }};

        final var body = RestResponse.<Object, Map<String, Object>>builder()
                .data(null)
                .errors(errors)
                .build();

        final var result = ResponseEntity.badRequest().body(body);
        return result;
    }
}
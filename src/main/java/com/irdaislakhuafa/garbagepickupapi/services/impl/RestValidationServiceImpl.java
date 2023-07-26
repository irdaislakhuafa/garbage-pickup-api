package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;

@Service
public class RestValidationServiceImpl implements RestValidationService {
    @Override
    public Map<String, Object> getListErrors(Errors errors) {
        final var results = new HashMap<String, Object>();
        errors.getFieldErrors().forEach(err -> results.put(err.getField(), err.getDefaultMessage()));
        return results;
    }
}
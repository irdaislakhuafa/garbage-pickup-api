package com.irdaislakhuafa.garbagepickupapi.services;

import org.springframework.validation.Errors;

import java.util.Map;

public interface RestValidationService {
    Map<String, Object> getListErrors(Errors errors);
}
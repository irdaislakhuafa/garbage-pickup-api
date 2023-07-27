package com.irdaislakhuafa.garbagepickupapi.models.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RestResponse<DATA, ERRORS> {
    private ERRORS errors;
    private DATA data;
}
package com.irdaislakhuafa.garbagepickupapi.models.gql.request.lastlocation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class lastLocation {
    double lat;
    double lng;
}
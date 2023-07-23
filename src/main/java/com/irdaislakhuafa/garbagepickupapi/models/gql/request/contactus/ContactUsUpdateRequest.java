package com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContactUsUpdateRequest {
    private String id;
    private String image;
    private String title;
    private String description;
}
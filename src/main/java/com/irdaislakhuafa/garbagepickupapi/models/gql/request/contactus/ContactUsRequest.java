package com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContactUsRequest {
    private MultipartFile image;
    private String title;
    private String description;
}
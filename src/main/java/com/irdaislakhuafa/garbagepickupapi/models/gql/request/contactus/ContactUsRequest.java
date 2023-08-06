package com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "cannot be null")
    private MultipartFile image;

    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String title;

    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String value;

    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    @NotNull(message = "cannot be null")
    private String description;
}
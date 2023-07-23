package com.irdaislakhuafa.garbagepickupapi.controllers.gql.contactus;

import com.irdaislakhuafa.garbagepickupapi.models.entities.ContactUs;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus.ContactUsRequest;
import com.irdaislakhuafa.garbagepickupapi.services.ContactUsService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "ContactUsMutation")
public class ContactUsMutation {
    private final ContactUsService contactUsService;

    @SchemaMapping
    public Optional<ContactUs> save(@Argument(name = "request") ContactUsRequest request) {
        final var contactUs = this.contactUsService.fromRequestToEntity(request);
        final var result = this.contactUsService.save(contactUs);
        return result;
    }
}
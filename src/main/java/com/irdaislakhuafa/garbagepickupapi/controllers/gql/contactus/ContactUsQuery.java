package com.irdaislakhuafa.garbagepickupapi.controllers.gql.contactus;

import com.irdaislakhuafa.garbagepickupapi.models.entities.ContactUs;
import com.irdaislakhuafa.garbagepickupapi.services.ContactUsService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "ContactUsQuery")
public class ContactUsQuery {
    private final ContactUsService contactUsService;

    @SchemaMapping
    public List<ContactUs> findAll() {
        final var results = this.contactUsService.findAll();
        return results;
    }
}
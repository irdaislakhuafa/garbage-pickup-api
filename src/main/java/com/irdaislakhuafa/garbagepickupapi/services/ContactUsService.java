package com.irdaislakhuafa.garbagepickupapi.services;

import com.irdaislakhuafa.garbagepickupapi.models.entities.ContactUs;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus.ContactUsRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus.ContactUsUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.converter.CRUDService;
import com.irdaislakhuafa.garbagepickupapi.services.converter.EntityConverterService;

public interface ContactUsService extends CRUDService<ContactUs, String>, EntityConverterService<ContactUs, ContactUsRequest, ContactUsUpdateRequest> {
}
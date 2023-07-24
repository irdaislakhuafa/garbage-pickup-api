package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.ContactUs;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus.ContactUsRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus.ContactUsUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.ContactUsRepository;
import com.irdaislakhuafa.garbagepickupapi.services.ContactUsService;
import com.irdaislakhuafa.garbagepickupapi.services.FileService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactUsServiceImpl implements ContactUsService {
    private final UserService userService;
    private final FileService fileService;
    private final ContactUsRepository contactUsRepository;

    @Override
    public Optional<ContactUs> save(ContactUs request) {
        try {
            request.setCreatedAt(LocalDateTime.now());
            request.setCreatedBy(this.userService.getCurrentUser().getId());

            final var result = this.contactUsRepository.save(request);
            return Optional.of(result);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<ContactUs> update(ContactUs request) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactUs> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<ContactUs> findById(String s) {
        return Optional.empty();
    }

    @Override
    public List<ContactUs> findAll() {
        try {
            final var results = this.contactUsRepository.findAll();
            return results;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ContactUs fromRequestToEntity(ContactUsRequest request) {
        try {
            var filePath = "";
            if (request.getImage() != null) {
                filePath = this.fileService.upload(request.getImage());
            }

            final var result = ContactUs.builder()
                    .image(filePath)
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .build();
            return result;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ContactUs fromUpdateRequestToEntity(ContactUsUpdateRequest request) {
        return null;
    }
}
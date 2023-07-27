package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.role.RoleRequest;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import com.irdaislakhuafa.garbagepickupapi.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = {"/api/rest/roles"})
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RestValidationService restValidationService;

    @PostMapping
    public ResponseEntity<RestResponse<Role, Map<String, Object>>> save(@RequestBody @Valid RoleRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<Role, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }
        
        final var role = this.roleService.fromRequestToEntity(request);
        final var result = this.roleService.save(role);
        return ResponseEntity.ok(RestResponse.<Role, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }
}
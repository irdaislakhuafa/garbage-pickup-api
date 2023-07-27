package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.role.RoleRequest;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import com.irdaislakhuafa.garbagepickupapi.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/rest/roles"})
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RestValidationService restValidationService;

    @Operation(summary = "Used to sace or create new role")
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

    @Operation(summary = "Used to find all roles")
    @GetMapping
    public ResponseEntity<RestResponse<List<Role>, Map<String, Object>>> findAll() {
        final var results = this.roleService.findAll();
        return ResponseEntity.ok(RestResponse.<List<Role>, Map<String, Object>>builder()
                .data(results)
                .build());
    }

    @Operation(summary = "Used to find role by id")
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<RestResponse<Role, Map<String, Object>>> findById(@PathVariable(name = "id") String id) {
        final var result = this.roleService.findById(id);
        return ResponseEntity.ok(RestResponse.<Role, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }
}
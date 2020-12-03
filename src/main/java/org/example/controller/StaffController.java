package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.StaffDto;
import org.example.service.StaffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/staff")
    public Collection<StaffDto> getAllStaffMembers(){
        return staffService.readAll()
                .stream()
                .map(model -> StaffDto.builder()
                    .firstName(model.getFirstName())
                    .lastName(model.getLastName())
                    .address(model.getAddress())
                    .email(model.getEmail())
                    .storeAddress(model.getStoreAddress())
                    .build()
                ).collect(Collectors.toList());
    }
}

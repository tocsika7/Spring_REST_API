package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.staff.StaffDto;
import org.example.controller.dto.staff.StaffRecordDto;
import org.example.controller.dto.staff.StaffUpdateDto;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.staff.StaffInUseException;
import org.example.exception.staff.UnknownStaffException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Staff;
import org.example.service.staff.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/staff")
    public void recordStaffMember(@RequestBody StaffRecordDto staffDto) {
        try {
            staffService.createStaffMember(new Staff(
                    staffDto.getFirstName(),
                    staffDto.getLastName(),
                    staffDto.getAddress(),
                    staffDto.getEmail(),
                    staffDto.getStoreAddress(),
                    staffDto.getUserName(),
                    staffDto.getPassword(),
                    staffDto.getActive()
            ));
        } catch (UnknownAddressException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/staff")
    public void updateStaffMember(@RequestBody StaffUpdateDto staffUpdateDto){
        try {
            staffService.updateStaffMember(staffUpdateDto.getStaffId(), new Staff(
                    staffUpdateDto.getFirstName(),
                    staffUpdateDto.getLastName(),
                    staffUpdateDto.getAddress(),
                    staffUpdateDto.getEmail(),
                    staffUpdateDto.getStoreAddress(),
                    staffUpdateDto.getUserName(),
                    staffUpdateDto.getPassword(),
                    staffUpdateDto.getActive()
            ));
        } catch (UnknownAddressException | UnknownStoreException | UnknownStaffException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/staff")
    public void deleteStaffMember(@RequestParam(name = "staffId", defaultValue = "1", required = true) int staffId){
        try {
            staffService.deleteStaffMember(staffId);
        } catch (UnknownStaffException | StaffInUseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

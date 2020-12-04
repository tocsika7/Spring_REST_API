package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.CustomerDto;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.customer.CustomerInUseException;
import org.example.exception.customer.UnkownCustomerException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Customer;
import org.example.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/customer")
    public Collection<CustomerDto> listAllCustomers(){
        return service.getAllCustomers()
                .stream()
                .map(model -> CustomerDto.builder()
                        .storeAddress(model.getStoreAddress())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .address(model.getAddress())
                        .email(model.getEmail())
                        .active(model.getActive())
                    .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/customer")
    public void createCustomer(@RequestBody CustomerDto customerDto){
        try {
            service.createCustomer(new Customer(
                    customerDto.getStoreAddress(),
                    customerDto.getFirstName(),
                    customerDto.getLastName(),
                    customerDto.getEmail(),
                    customerDto.getAddress(),
                    customerDto.getActive()
            ));
        } catch (UnknownAddressException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/customer")
    public void deleteCustomer(@RequestParam(name = "email", defaultValue = "example@test.com", required = true) String email){
        try {
            service.deleteCustomer(email);
        } catch (UnkownCustomerException | CustomerInUseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}

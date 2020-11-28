package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.CustomerDto;
import org.example.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
                        .storeId(model.getStoreId())
                        .firstName(model.getFirstName())
                        .lastName(model.getLastName())
                        .addressId(model.getAddressId())
                        .active(model.getActive())
                    .build())
                .collect(Collectors.toList());
    }
}

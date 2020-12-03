package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.AddressDto;
import org.example.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address")
    public Collection<AddressDto> listAllAddresses(){
        return addressService.getAll()
                .stream()
                .map(model -> AddressDto.builder()
                    .address(model.getAddress())
                    .address2(model.getAddress2())
                    .district(model.getDistrict())
                    .cityName(model.getCityName())
                    .postalCode(model.getPostalCode())
                    .phone(model.getPhone())
                    .build()
                ).collect(Collectors.toList());
    }
}

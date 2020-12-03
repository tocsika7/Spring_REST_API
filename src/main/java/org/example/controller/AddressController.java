package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.AddressDto;
import org.example.exception.city.UnknownCityException;
import org.example.model.Address;
import org.example.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/address")
    public void recordAddress(@RequestBody AddressDto addressDto){
        try {
            addressService.createAddress(new Address(
                    addressDto.getAddress(),
                    addressDto.getAddress2(),
                    addressDto.getDistrict(),
                    addressDto.getCityName(),
                    addressDto.getPostalCode(),
                    addressDto.getPhone()
            ));
        } catch (UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid city");
        }
    }
}

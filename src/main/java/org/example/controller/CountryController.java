package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.CountryDto;
import org.example.exception.InvalidCountryException;
import org.example.model.Country;
import org.example.service.CountryService;
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
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/country")
    public Collection<CountryDto> listAllCountries(){
        return countryService.getAllCountries()
                .stream()
                .map(model -> CountryDto.builder()
                    .country(model.getCountry())
                        .build()
                ).collect(Collectors.toList());
    }

    @PostMapping("/country")
    public void recordCountry(@RequestBody CountryDto  countryDto){
        try {
            countryService.recordCounty(new Country(
                    countryDto.getCountry()
            ));
        } catch (InvalidCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

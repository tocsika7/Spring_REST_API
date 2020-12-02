package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.CountryDto;
import org.example.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    .countryId(model.getCountyId())
                    .country(model.getCountry())
                        .build()
                ).collect(Collectors.toList());
    }
}

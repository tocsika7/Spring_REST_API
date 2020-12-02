package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.CountryDto;
import org.example.controller.dto.CountryUpdateDto;
import org.example.exception.CountryInUseException;
import org.example.exception.InvalidCountryException;
import org.example.exception.UnknownCountryException;
import org.example.model.Country;
import org.example.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @DeleteMapping("/country")
    public void deleteCountry(@RequestBody CountryDto countryDto){
        try{
            countryService.deleteCountry(new Country(countryDto.getCountry()));
        }
        catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        catch (CountryInUseException e){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/country")
    public void updateCountry(@RequestBody CountryUpdateDto countryUpdateDto){
        try{
            countryService.updateCountry(new Country(countryUpdateDto.getCountry()),new Country(countryUpdateDto.getNewCountry()));
        }
        catch (InvalidCountryException | UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }
}

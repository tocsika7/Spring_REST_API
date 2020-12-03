package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.CityDto;
import org.example.controller.dto.CityUpdateDto;
import org.example.exception.city.CityInUseException;
import org.example.exception.city.InvalidCityException;
import org.example.exception.city.UnknownCityException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.City;
import org.example.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CityController {

    private final CityService cityService;

    @GetMapping("/city")
    public Collection<CityDto> listAllCountries(){
        return cityService.getAllCities()
                .stream()
                .map(model -> CityDto.builder()
                        .city(model.getCity())
                        .country(model.getCountry())
                        .build()
                ).collect(Collectors.toList());
    }

    @PostMapping("/city")
    public void recordCity(@RequestBody CityDto cityDto){
        try {
            cityService.recordCity(new City(cityDto.getCity(), cityDto.getCountry()));
        }
        catch (InvalidCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid city");
        }
        catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown country");
        }


    }

    @DeleteMapping("/city")
    public void deleteCity(@RequestBody CityDto cityDto){
        try {
            cityService.deleteCity(new City(cityDto.getCity(), cityDto.getCountry()));
        } catch (CityInUseException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/city")
    public void updateCity(@RequestBody CityUpdateDto cityUpdateDto){
        try {
            cityService.updateCity(
                    cityUpdateDto.getCity(),
                    new City(cityUpdateDto.getNewCity(),
                            cityUpdateDto.getNewCountry()));
        } catch (UnknownCountryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UnknownCityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (InvalidCityException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

    }

}

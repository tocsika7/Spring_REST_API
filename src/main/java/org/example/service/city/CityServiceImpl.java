package org.example.service.city;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.city.CityDao;
import org.example.exception.city.CityInUseException;
import org.example.exception.city.InvalidCityException;
import org.example.exception.city.UnknownCityException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.City;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityDao cityDao;

    @Override
    public Collection<City> getAllCities() {
        return cityDao.readAll();
    }

    @Override
    public void recordCity(City city) throws UnknownCountryException, InvalidCityException {
        cityDao.createCity(city);
    }

    @Override
    public void deleteCity(City city) throws UnknownCityException, CityInUseException {
        cityDao.deleteCity(city);
    }

    @Override
    public void updateCity(String cityName, City newCity) throws InvalidCityException, UnknownCityException, UnknownCountryException {
        cityDao.updateCity(cityName, newCity);
    }
}

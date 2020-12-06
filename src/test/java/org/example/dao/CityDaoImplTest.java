package org.example.dao;


import org.example.dao.city.CityDaoImpl;
import org.example.dao.city.CityRepository;
import org.example.dao.country.CountryRepository;
import org.example.dao.entity.CityEntity;
import org.example.dao.entity.CountryEntity;
import org.example.exception.city.CityInUseException;
import org.example.exception.city.InvalidCityException;
import org.example.exception.city.UnknownCityException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityDaoImplTest {

    @Spy
    @InjectMocks
    private CityDaoImpl cityDao;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CountryRepository countryRepository;

    @Test
    public void createCitySuccessful() throws UnknownCountryException, InvalidCityException {
        doReturn(CountryEntity.builder().country("Afghanistan").build())
                .when(cityDao).queryCountry(anyString());
        cityDao.createCity(getValidCityToCreate());
        verify(cityRepository,times(1)).save(any());
    }

    @Test
    public void createCityUnknownCountry() {
        assertThrows(UnknownCountryException.class, ()-> {
            cityDao.createCity(new City("Random", "Random"));
        });
    }

    @Test
    public void deleteCitySuccessful() throws UnknownCityException, CityInUseException {
        doReturn(CityEntity.builder().city("CityToDelete").build())
                .when(cityRepository).findFirstByCity(any());
        cityDao.deleteCity(new City("CityToDelete", "Afghanistan"));
        verify(cityRepository, times(1)).delete(any());
    }

    @Test
    public void deleteCityUnknownCity() {
        assertThrows(UnknownCityException.class, ()->{
            cityDao.deleteCity(new City("Invalid", "Brazil"));
        });
    }

    @Test
    public void updateCitySuccessful() throws UnknownCountryException, InvalidCityException, UnknownCityException {
        doReturn(CityEntity.builder().city("CityToDelete").build())
                .when(cityRepository).findFirstByCity(any());
        doReturn(CountryEntity.builder().country("Spain").build())
                .when(cityDao).queryCountry(anyString());
        cityDao.updateCity("CityToDelete",new City("CityToUpdate", "Spain"));
        verify(cityRepository,times(1)).save(any());
    }

    @Test
    public void updateCityUnknownCity() {
        assertThrows(UnknownCityException.class, ()->{
            cityDao.updateCity("Invalid",new City("Invalid", "Brazil"));
        });
    }

    @Test
    public void updateCityUnknownCountry(){
        doReturn(CityEntity.builder().city("CityToDelete").build())
                .when(cityRepository).findFirstByCity(any());
        assertThrows(UnknownCountryException.class, ()->{
            cityDao.updateCity("CityToDelete",
                    new City("Updated City", "Invalid"));
        });
    }

    public City getValidCityToCreate() {
        return new City(
                "New City",
                "Afghanistan"
        );
    }

}

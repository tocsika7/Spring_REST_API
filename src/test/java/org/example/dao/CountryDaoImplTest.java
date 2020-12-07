package org.example.dao;

import org.example.dao.country.CountryDaoImpl;
import org.example.dao.country.CountryRepository;
import org.example.dao.entity.CountryEntity;
import org.example.exception.country.CountryInUseException;
import org.example.exception.country.InvalidCountryException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryDaoImplTest {

    @InjectMocks
    private CountryDaoImpl countryDao;

    @Mock
    private CountryRepository countryRepository;

    @Test
    void createCountrySuccessful() throws InvalidCountryException {
        countryDao.createCountry(new Country("New Country"));
        verify(countryRepository, times(1)).save(any());
    }

    @Test
    void deleteCountrySuccessful() throws UnknownCountryException, CountryInUseException {
        doReturn(CountryEntity.builder().country("CountryToDelete").build())
                .when(countryRepository).findFirstByCountry(any());
        countryDao.deleteCountry(new Country("CountryToDelete"));
        verify(countryRepository, times(1)).delete(any());
    }

    @Test
    void deleteCountryUnknownCountry() {
        assertThrows(UnknownCountryException.class, ()-> {
            countryDao.deleteCountry(new Country("Invalid Country"));
        });
    }

    @Test
    void updateCountrySuccessful() throws UnknownCountryException, InvalidCountryException {
        doReturn(CountryEntity.builder().country("CountryToDelete").build())
                .when(countryRepository).findFirstByCountry("CountryToDelete");
        countryDao.updateCountry(new Country("CountryToDelete"), new Country("CountryToUpdate"));
        verify(countryRepository, times(1)).save(any());
    }

    @Test
    void updateCountryUnknownCountry() {
        assertThrows(UnknownCountryException.class, ()-> {
            countryDao.updateCountry(new Country("Invalid Country"), new Country("Updated"));
        });
    }

}

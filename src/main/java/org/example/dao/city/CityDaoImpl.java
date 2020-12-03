package org.example.dao.city;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.country.CountryRepository;
import org.example.dao.entity.CityEntity;
import org.example.dao.entity.CountryEntity;
import org.example.exception.city.CityInUseException;
import org.example.exception.city.InvalidCityException;
import org.example.exception.city.UnknownCityException;
import org.example.exception.country.UnknownCountryException;
import org.example.model.City;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityDaoImpl implements CityDao {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    protected Optional<CityEntity> queryCity(City city) {
        return StreamSupport.stream(cityRepository.findAll().spliterator(),false)
                .filter(cityEntity -> city.getCity().equals(cityEntity.getCity())).findFirst();
    }

    protected CountryEntity queryCountry(String country) throws UnknownCountryException {

        Optional<CountryEntity> countryEntity = StreamSupport.stream(countryRepository.findAll().spliterator(),false)
                .filter(entity -> country.equals(entity.getCountry())).findFirst();
        if(countryEntity.isEmpty()){
            throw new UnknownCountryException("No country with this id");
        }
        return countryEntity.get();
    }

    @Override
    public Collection<City> readAll() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(),false)
                .map(cityEntity -> new City(
                        cityEntity.getCity(),
                        cityEntity.getCountry().getCountry()
                )).collect(Collectors.toList());
    }

    @Override
    public void createCity(City city) throws UnknownCountryException, InvalidCityException {
        CityEntity cityEntity;
        cityEntity = CityEntity.builder()
                .city(city.getCity())
                .country(queryCountry(city.getCountry()))
                .lastUpdate(new Timestamp((new Date()).getTime()))
                .build();
        log.info("CityEntity: {}", cityEntity.toString());
        try{
            cityRepository.save(cityEntity);
        } catch (Exception e){
            log.error(e.getMessage());
            throw new InvalidCityException();
        }
    }

    @Override
    public void deleteCity(City city) throws UnknownCityException, CityInUseException {
        Optional<CityEntity> cityEntity = queryCity(city);
        if(cityEntity.isEmpty()){
            throw new UnknownCityException("City not found");
        }
        try {
            cityRepository.delete(cityEntity.get());
        } catch (Exception e){
            log.error(e.getMessage());
            throw new CityInUseException("City is used be an other table");
        }

    }

    @Override
    public void updateCity(String cityName, City newCity) throws UnknownCityException, UnknownCountryException, InvalidCityException {
        Optional<CityEntity> cityEntity = Optional.ofNullable(cityRepository.findFirstByCity(cityName));
        if(cityEntity.isEmpty()){
            throw new UnknownCityException("City not found");
        }
        else {
            CityEntity newCityEntity = CityEntity.builder()
                    .cityId(cityEntity.get().getCityId())
                    .city(newCity.getCity())
                    .country(queryCountry(newCity.getCountry()))
                    .lastUpdate(new Timestamp((new Date()).getTime()))
                    .build();
            log.info("CityEntity Updated:" + newCityEntity.toString());
            try{
                cityRepository.save(newCityEntity);
            } catch (Exception e){
                log.error("Error while updating city: " + e.getMessage());
                throw new InvalidCityException();
            }
        }

    }
}

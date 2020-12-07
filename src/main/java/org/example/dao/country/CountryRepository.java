package org.example.dao.country;

import org.example.dao.entity.CountryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {
    CountryEntity findFirstByCountry(String country);
}

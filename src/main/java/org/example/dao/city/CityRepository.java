package org.example.dao.city;

import org.example.dao.entity.CityEntity;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<CityEntity, Integer> {
}

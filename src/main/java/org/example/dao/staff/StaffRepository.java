package org.example.dao.staff;

import org.example.dao.entity.StaffEntity;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<StaffEntity, Integer> {
}

package org.example.dao.staff;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Staff;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffDaoImpl implements StaffDao {

    private final StaffRepository staffRepository;

    @Override
    public Collection<Staff> readAll() {
        return StreamSupport.stream(staffRepository.findAll().spliterator(),false)
                .map(staffEntity -> new Staff(
                      staffEntity.getFirstName(),
                      staffEntity.getLastName(),
                      staffEntity.getAddress().getAddress(),
                      staffEntity.getEmail(),
                      staffEntity.getStore().getAddress().getAddress(),
                      staffEntity.getUserName(),
                      staffEntity.getPassword(),
                      staffEntity.getActive()
                )).collect(Collectors.toList());
    }
}

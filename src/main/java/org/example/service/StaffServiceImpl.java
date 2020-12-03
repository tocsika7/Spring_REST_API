package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.staff.StaffDao;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Staff;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffDao staffDao;

    @Override
    public Collection<Staff> readAll() {
        return staffDao.readAll();
    }

    @Override
    public void createStaffMember(Staff staff) throws UnknownAddressException, UnknownStoreException {
        staffDao.createStaffMember(staff);
    }
}

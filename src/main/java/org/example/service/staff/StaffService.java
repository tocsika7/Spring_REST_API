package org.example.service.staff;

import org.example.exception.address.UnknownAddressException;
import org.example.exception.staff.InvalidStaffException;
import org.example.exception.staff.StaffInUseException;
import org.example.exception.staff.UnknownStaffException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Staff;

import java.util.Collection;

public interface StaffService {

    Collection<Staff> readAll();
    void createStaffMember(Staff staff) throws UnknownAddressException, UnknownStoreException, InvalidStaffException;
    void updateStaffMember(int staffId, Staff staff) throws UnknownAddressException, UnknownStaffException, UnknownStoreException, InvalidStaffException;
    void deleteStaffMember(int staffId) throws StaffInUseException, UnknownStaffException;
}

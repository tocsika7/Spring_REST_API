package org.example.dao.staff;

import org.example.exception.address.UnknownAddressException;
import org.example.exception.staff.StaffInUseException;
import org.example.exception.staff.UnknownStaffException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Staff;

import java.util.Collection;

public interface StaffDao {

    Collection<Staff> readAll();
    void createStaffMember(Staff staff) throws UnknownAddressException, UnknownStoreException;
    void updateStaffMember(int staffId, Staff staff) throws UnknownStaffException, UnknownAddressException, UnknownStoreException;
    void deleteStaffMember(int staffId) throws UnknownStaffException, StaffInUseException;
}

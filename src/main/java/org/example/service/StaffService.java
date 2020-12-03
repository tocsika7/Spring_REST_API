package org.example.service;

import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Staff;

import java.util.Collection;

public interface StaffService {

    Collection<Staff> readAll();
    void createStaffMember(Staff staff) throws UnknownAddressException, UnknownStoreException;
}

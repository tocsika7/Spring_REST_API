package org.example.dao.staff;

import org.example.model.Staff;

import java.util.Collection;

public interface StaffDao {

    Collection<Staff> readAll();
}

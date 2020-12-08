package org.example.dao;

import org.example.dao.address.AddressRepository;
import org.example.dao.entity.AddressEntity;
import org.example.dao.entity.StaffEntity;
import org.example.dao.entity.StoreEntity;
import org.example.dao.staff.StaffDaoImpl;
import org.example.dao.staff.StaffRepository;
import org.example.dao.store.StoreRepository;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.staff.InvalidStaffException;
import org.example.exception.staff.StaffInUseException;
import org.example.exception.staff.UnknownStaffException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Staff;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffDaoImplTest {

    @Spy
    @InjectMocks
    private StaffDaoImpl staffDao;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreRepository storeRepository;

    @Test
    public void createStaffSuccessful() throws UnknownAddressException, UnknownStoreException, InvalidStaffException {
        doReturn(AddressEntity.builder().address("1121 Loja Avenue").build())
                .when(staffDao).queryAddress(anyString());
        doReturn(StoreEntity.builder().address(AddressEntity.builder().address("47 MySakila Drive").build()).build())
                .when(staffDao).queryStore(any());
        staffDao.createStaffMember(getStaff());
        verify(staffRepository,times(1)).save(any());
    }

    @Test
    public void createStaffUnknownAddress(){
        assertThrows(UnknownAddressException.class, ()->{
           staffDao.createStaffMember(getStaffUnknownAddress());
        });
    }

    @Test
    public void createStaffUnknownStore() throws UnknownAddressException {
        doReturn(AddressEntity.builder().address("1121 Loja Avenue").build())
                .when(staffDao).queryAddress(anyString());
        assertThrows(UnknownStoreException.class, ()->{
            staffDao.createStaffMember(getStaffUnknownStore());
        });
    }

    @Test
    public void deleteStaffSuccessful() throws StaffInUseException, UnknownStaffException {
        doReturn(getStaffToDelete())
                .when(staffRepository).findById(anyInt());
        staffDao.deleteStaffMember(3);
        verify(staffRepository,times(1)).delete(any());
    }

    @Test
    public void deleteStaffUnknownStaff(){
        assertThrows(UnknownStaffException.class, ()->{
            staffDao.deleteStaffMember(-1);
        });
    }

    @Test
    public void updateStaffSuccessful() throws UnknownAddressException, UnknownStoreException, UnknownStaffException, InvalidStaffException {
        doReturn(getStaffToDelete())
                .when(staffRepository).findById(anyInt());
        doReturn(AddressEntity.builder().address("1121 Loja Avenue").build())
                .when(staffDao).queryAddress(anyString());
        doReturn(StoreEntity.builder().address(AddressEntity.builder().address("47 MySakila Drive").build()).build())
                .when(staffDao).queryStore(any());
        staffDao.updateStaffMember(3,getStaff());
        verify(staffRepository,times(1)).save(any());
    }

    @Test
    public void updateStaffUnknownStaff(){
        assertThrows(UnknownStaffException.class, ()->{
            staffDao.updateStaffMember(-1,getStaff());
        });
    }

    @Test
    public void updateStaffUnknownAddress(){
        doReturn(getStaffToDelete())
                .when(staffRepository).findById(anyInt());
        assertThrows(UnknownAddressException.class, ()->{
            staffDao.updateStaffMember(3,getStaffUnknownAddress());
        });
    }

    @Test
    public void updateStaffUnknownStore() throws UnknownAddressException {
        doReturn(getStaffToDelete())
                .when(staffRepository).findById(anyInt());
        doReturn(AddressEntity.builder().address("1121 Loja Avenue").build())
                .when(staffDao).queryAddress(anyString());
        assertThrows(UnknownStoreException.class, ()->{
            staffDao.updateStaffMember(3,getStaffUnknownStore());
        });

    }

    public Staff getStaff(){
        return new Staff(
                "John",
                "Doe",
                "1121 Loja Avenue",
                "john@gmail.com",
                "47 MySakila Drive",
                "john123",
                "password",
                1
                );
    }

    public Staff getStaffUnknownAddress(){
        return new Staff(
                "John",
                "Doe",
                "",
                "john@gmail.com",
                "47 MySakila Drive",
                "john123",
                "password",
                1
        );
    }

    public Staff getStaffUnknownStore(){
        return new Staff(
                "John",
                "Doe",
                "1121 Loja Avenue",
                "john@gmail.com",
                "",
                "john123",
                "password",
                1
        );
    }

    public Optional<StaffEntity> getStaffToDelete(){
        Optional<StaffEntity> staffEntity = Optional.ofNullable(StaffEntity
                .builder().email("john@gmail.com").build());
        return staffEntity;
    }

}

package org.example.dao.staff;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.address.AddressRepository;
import org.example.dao.entity.AddressEntity;
import org.example.dao.entity.StaffEntity;
import org.example.dao.entity.StoreEntity;
import org.example.dao.store.StoreRepository;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.staff.StaffInUseException;
import org.example.exception.staff.UnknownStaffException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Staff;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffDaoImpl implements StaffDao {

    private final StaffRepository staffRepository;
    private final AddressRepository addressRepository;
    private final StoreRepository storeRepository;

    public AddressEntity queryAddress(String addressName) throws UnknownAddressException {
        Optional<AddressEntity> addressEntity = Optional.ofNullable(
                addressRepository.findFirstByAddress(addressName));
        if(addressEntity.isEmpty()){
            throw new UnknownAddressException("Address not found " + addressName);
        }
        return addressEntity.get();
    }

    public StoreEntity queryStore(String storeAddress) throws UnknownStoreException {
        Optional<StoreEntity> storeEntity = Optional.ofNullable(
                storeRepository.findFirstByAddress_Address(storeAddress));
        if(storeEntity.isEmpty()){
            throw new UnknownStoreException("Store not found " + storeAddress);
        }
        return storeEntity.get();
    }

    protected SerialBlob blobFactory() throws SQLException {
        SecureRandom random = new SecureRandom();
        Blob blob = null;
        byte[] blobBytes = new byte[20];
        random.nextBytes(blobBytes);
        return new SerialBlob(blobBytes);

    }

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

    @Override
    public void createStaffMember(Staff staff) throws UnknownAddressException, UnknownStoreException {
        StaffEntity staffEntity;
        try {
            staffEntity = StaffEntity.builder()
                    .firstName(staff.getFirstName())
                    .lastName(staff.getLastName())
                    .address(queryAddress(staff.getAddress()))
                    .email(staff.getEmail())
                    .store(queryStore(staff.getStoreAddress()))
                    .userName(staff.getUserName())
                    .password(staff.getPassword())
                    .picture(blobFactory())
                    .active(staff.getActive())
                    .lastUpdate(new Timestamp(new Date().getTime()))
                    .build();
            log.info("Staff member added:  " + staff.getFirstName());
            try {
                staffRepository.save(staffEntity);
            } catch (Exception e) {
                log.error("Error while saving staff member: " + e.getMessage());
            }
        } catch (SQLException e) {
            log.error("Blob factory error: " + e.getMessage());
        }
    }

    @Override
    public void updateStaffMember(int staffId, Staff staff) throws UnknownStaffException, UnknownAddressException, UnknownStoreException {
        Optional<StaffEntity> staffEntity = staffRepository.findById(staffId);
        if(staffEntity.isEmpty()){
            log.error(String.format("Staff with id:%d not found", staffId));
            throw new UnknownStaffException(
                    String.format("Staff with id:%d not found", staffId));
        }
        else {
            try {
                StaffEntity newStaffEntity = StaffEntity.builder()
                        .staffId(staffId)
                        .firstName(staff.getFirstName())
                        .lastName(staff.getLastName())
                        .address(queryAddress(staff.getAddress()))
                        .email(staff.getEmail())
                        .store(queryStore(staff.getStoreAddress()))
                        .userName(staff.getUserName())
                        .password(staff.getPassword())
                        .picture(blobFactory())
                        .active(staff.getActive())
                        .lastUpdate(new Timestamp(new Date().getTime()))
                        .build();
                log.info(String.format("Staff Member updated with id: %d", staffId));
                try{
                    staffRepository.save(newStaffEntity);
                } catch (Exception e) {
                    log.error("Error while updating Staff Member: " + e.getMessage());
                }
            } catch (SQLException e){
                log.error("Blob Factory error: " + e.getMessage());
            }
        }
    }

    @Override
    public void deleteStaffMember(int staffId) throws UnknownStaffException, StaffInUseException {
        Optional<StaffEntity> staffEntity = staffRepository.findById(staffId);
        if(staffEntity.isEmpty()) {
            throw new UnknownStaffException(String.format
                    ("Staff member with id:%d not found", staffId));
        }
        try {
            staffRepository.delete(staffEntity.get());
        } catch (Exception e) {
            log.error("Error while deleting staff member." + e.getMessage());
            throw new StaffInUseException(String.format
                    ("Staff member with id:%d is used by another table", staffId));
        }
    }
}

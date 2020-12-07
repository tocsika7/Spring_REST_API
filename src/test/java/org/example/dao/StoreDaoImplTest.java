package org.example.dao;

import org.example.dao.address.AddressRepository;
import org.example.dao.entity.AddressEntity;
import org.example.dao.entity.StoreEntity;
import org.example.dao.store.StoreDaoImpl;
import org.example.dao.store.StoreRepository;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Store;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StoreDaoImplTest {

    @Spy
    @InjectMocks
    private StoreDaoImpl storeDao;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private AddressRepository addressRepository;

    @Test
    public void readByIdUnknownStore() {
        assertThrows(UnknownStoreException.class, ()-> {
            storeDao.readById(-1);
        });
    }

    @Test
    public void updateStoreAddressSuccessful() throws UnknownAddressException, UnknownStoreException {
        doReturn(StoreEntity.builder().
                address(AddressEntity.builder().address("808 Bhopal Manor").build()).build())
                .when(storeRepository).findFirstByAddress_Address(any());
        doReturn(AddressEntity.builder().address("23 Workhaven Lane").build())
                .when(storeDao).queryAddress(anyString());
        storeDao.updateStoreAddress("808 Bhopal Manor","23 Workhaven Lane");
        verify(storeRepository,times(1)).save(any());
    }

    @Test
    public void updateStoreAddressUnknownStore() {
        assertThrows(UnknownStoreException.class, ()->{
            storeDao.updateStoreAddress("Not Real","23 Workhaven Lane");
        });
    }

    @Test
    public void updateStoreAddressUnknownAddress(){
        doReturn(StoreEntity.builder().
                address(AddressEntity.builder().address("808 Bhopal Manor").build()).build())
                .when(storeRepository).findFirstByAddress_Address(any());
        assertThrows(UnknownAddressException.class, ()->{
            storeDao.updateStoreAddress("808 Bhopal Manor","Not Real");
        });
    }



}

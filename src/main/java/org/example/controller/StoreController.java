package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.store.StoreDto;
import org.example.controller.dto.store.StoreUpdateDto;
import org.example.exception.address.UnknownAddressException;
import org.example.exception.store.UnknownStoreException;
import org.example.model.Store;
import org.example.service.store.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/stores")
    public Collection<StoreDto> getAllStores(){
        return storeService.readAll()
                .stream().map(
                        model -> StoreDto.builder()
                        .staffEmail(model.getStaffEmail())
                        .storeAddress(model.getStoreAddress())
                        .build()
                ).collect(Collectors.toList());
    }

    @GetMapping("/store")
    public StoreDto getStoreById(@RequestParam(name = "storeId", defaultValue = "1", required = true) int storeId){
        try {
            Store store = storeService.readById(storeId);
            return new StoreDto(store.getStaffEmail(),store.getStoreAddress());
        } catch (UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/store")
    public void updateStoreAddress(@RequestBody StoreUpdateDto storeUpdateDto){
        try {
            storeService.updateStoreAddress(storeUpdateDto.getAddress(),storeUpdateDto.getNewAddress());
        } catch (UnknownAddressException | UnknownStoreException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.dto.StoreDto;
import org.example.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/store")
    public Collection<StoreDto> getAllStores(){
        return storeService.readAll()
                .stream().map(
                        model -> StoreDto.builder()
                        .staffEmail(model.getStaffEmail())
                        .storeAddress(model.getStoreAddress())
                        .build()
                ).collect(Collectors.toList());
    }
}

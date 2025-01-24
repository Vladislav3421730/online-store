package com.example.webapp.service.impl;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.mapper.AddressMapper;
import com.example.webapp.model.Address;
import com.example.webapp.repository.AddressRepository;
import com.example.webapp.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AddressServiceImpl implements AddressService {

    AddressRepository addressRepository;
    AddressMapper addressMapper;

    @Override
    @Transactional
    public AddressDto save(AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        addressRepository.save(address);
        log.info("Address saved successfully: {}", addressDto);

        return addressMapper.toDTO(address);
    }
}

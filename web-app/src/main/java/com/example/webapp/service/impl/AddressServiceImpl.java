package com.example.webapp.service.impl;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.mapper.AddressMapper;
import com.example.webapp.mapper.AddressMapperImpl;
import com.example.webapp.model.Address;
import com.example.webapp.repository.AddressRepository;
import com.example.webapp.service.AddressService;
import com.example.webapp.utils.HibernateValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    private final AddressMapper addressMapper = new AddressMapperImpl();

    @Override
    public AddressDto save(AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        Set<ConstraintViolation<Address>> violations = HibernateValidatorUtil.getValidator().validate(address);
        if (!violations.isEmpty()) {
            log.error("Validation failed for address: {}", addressDto);
            throw new ConstraintViolationException(violations);
        }
        addressRepository.save(address);
        log.info("Address saved successfully: {}", addressDto);

        return addressMapper.toDTO(address);
    }
}

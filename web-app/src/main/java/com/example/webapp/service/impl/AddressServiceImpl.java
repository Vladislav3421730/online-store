package com.example.webapp.service.impl;

import com.example.webapp.dao.impl.AddressDaoImpl;
import com.example.webapp.dto.AddressDto;
import com.example.webapp.mapper.AddressMapper;
import com.example.webapp.mapper.AddressMapperImpl;
import com.example.webapp.model.Address;
import com.example.webapp.model.Product;
import com.example.webapp.service.AddressService;
import com.example.webapp.utils.HibernateValidatorUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressServiceImpl implements AddressService {

    private final static AddressService INSTANCE = new AddressServiceImpl();
    public static AddressService getInstance(){
        return INSTANCE;
    }

    private final AddressDaoImpl addressDao = AddressDaoImpl.getInstance();
    private final AddressMapper addressMapper = new AddressMapperImpl();

    @Override
    public void save(AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        Set<ConstraintViolation<Address>> violations = HibernateValidatorUtil.getValidator().validate(address);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        addressDao.save(address);
    }
}

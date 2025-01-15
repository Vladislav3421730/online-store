package com.example.webapp.mapper;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.model.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    Address toEntity(AddressDto addressDto);

    AddressDto toDTO(Address address);
}

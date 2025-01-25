package com.example.webapp.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
public class AddressDto {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String region;
    private String town;
    private String exactAddress;
    private String postalCode;
}

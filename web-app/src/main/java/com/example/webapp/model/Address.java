package com.example.webapp.model;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "region must be not empty and blank")
    private String region;

    @NotBlank(message = "town must be not empty and blank")
    private String town;

    @NotBlank(message = "exact address must be not empty anf blank")
    @Column(name = "exact_address")
    private String exactAddress;

    @Column(name = "postal_code")
    private String postalCode;

}

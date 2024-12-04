package com.example.webapp.model;

import jdk.jfr.Experimental;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
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

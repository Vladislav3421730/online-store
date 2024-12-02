package com.example.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3,message = "title's size must more or equal than 3")
    @NotBlank
    private String title;
    @Column(columnDefinition = "TEXT")
    @Size(min = 10,message = "description's size must more or equal than 10")
    @NotBlank
    private String description;
    @Size(min = 3,message = "category's size must more or equal than 3")
    @NotBlank
    private String category;
    @Min(value = 20,message = "amount must be more or equal than 20")
    private int amount;
    @DecimalMin(value = "0.01", message = "Cost must be greater than or equal to 10.3")
    private BigDecimal coast;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "product",orphanRemoval = true)
    private List<Image> imageList=new ArrayList<>();

    public void addImageToList(Image image){
        imageList.add(image);
        image.setProduct(this);
    }
}

package com.example.webapp.utils;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@UtilityClass
public class PredicateFormationAssistant {

    public List<Predicate> createFromDto(ProductFilterDTO productFilterDTO, CriteriaBuilder cb, Root<Product> root){
        List<Predicate> predicates = new ArrayList<>();
        if(productFilterDTO.getTitle()!=null && !productFilterDTO.getTitle().isBlank()){
            log.info("filter title (allowed in search input) {} was added to predicates",productFilterDTO.getTitle());
            predicates.add(cb.like(cb.lower(root.get("title")),"%"+productFilterDTO.getTitle().toLowerCase()+"%"));
        }
        if(productFilterDTO.getCategory()!=null && !productFilterDTO.getCategory().isBlank()){
            log.info("filter category {} was added to predicates",productFilterDTO.getCategory());
            predicates.add(cb.like(cb.lower(root.get("category")),"%"+productFilterDTO.getCategory().toLowerCase()+"%"));
        }
        if(productFilterDTO.getMinPrice()!=null){
            log.info("filter minPrice {} was added to predicates",productFilterDTO.getMinPrice());
            predicates.add(cb.greaterThanOrEqualTo(root.get("coast"),productFilterDTO.getMinPrice()));
        }
        if(productFilterDTO.getMaxPrice()!=null){
            log.info("filter maxPrice {} was added to predicates",productFilterDTO.getMaxPrice());
            predicates.add(cb.lessThanOrEqualTo(root.get("coast"),productFilterDTO.getMaxPrice()));
        }
        return predicates;
    }


}

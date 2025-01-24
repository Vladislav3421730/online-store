package com.example.webapp.repository;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.utils.PredicateFormationAssistant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager entityManager;

    @Override
    public List<Product> findAllByFilter(ProductFilterDTO productFilterDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = PredicateFormationAssistant.createFromDto(productFilterDTO, cb, root);
        log.info("size {}", predicates.size());
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size() - 1]));
        }
        if (productFilterDTO.getSort() != null) {
            switch (productFilterDTO.getSort()) {
                case "cheap" -> query.orderBy(cb.asc(root.get("coast")));
                case "expensive" -> query.orderBy(cb.desc(root.get("coast")));
                case "alphabet" -> query.orderBy(cb.asc(root.get("title")));
            }
        }
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public List<Product> findAllByPriceFilter(ProductFilterDTO productFilterDTO, int initIndex) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = PredicateFormationAssistant.createFromDto(productFilterDTO, cb, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size() - 1]));
        }
        query.orderBy(cb.asc(root.get("id")));
        return entityManager.createQuery(query)
                .setFirstResult(initIndex)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public int getTotalAmountByFilter(ProductFilterDTO productFilterDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product> root = query.from(Product.class);

        List<Predicate> predicates = PredicateFormationAssistant.createFromDto(productFilterDTO, cb, root);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        query.select(cb.count(root));
        return entityManager.createQuery(query)
                .getSingleResult()
                .intValue();
    }
}

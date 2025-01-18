package com.example.webapp.dao.impl;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.dao.AbstractHibernateDao;
import com.example.webapp.dao.ProductDao;
import com.example.webapp.utils.PredicateFormationAssistant;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@Slf4j
public class ProductDaoImpl extends AbstractHibernateDao<Product> implements ProductDao {

    private static final ProductDaoImpl INSTANCE = new ProductDaoImpl();

    public static ProductDaoImpl getInstance() {
        return INSTANCE;
    }

    private ProductDaoImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> findAllByTitle(String title) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "from Product p where lower(p.title) like lower(:title)", Product.class)
                    .setParameter("title", "%" + title.toLowerCase() + "%")
                    .getResultList();
        }
    }

    @Override
    public List<Product> findAllByFilter(ProductFilterDTO productFilterDTO) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
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
            return session.createQuery(query).getResultList();
        }

    }

    @Override
    public List<Product> findAllByPriceFilter(ProductFilterDTO productFilterDTO, int initIndex) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            List<Predicate> predicates = PredicateFormationAssistant.createFromDto(productFilterDTO, cb, root);
            if (!predicates.isEmpty()) {
                query.where(predicates.toArray(new Predicate[predicates.size() - 1]));
            }
            query.orderBy(cb.asc(root.get("id")));
            return session.createQuery(query)
                    .setFirstResult(initIndex)
                    .setMaxResults(10)
                    .getResultList();
        }
    }

    @Override
    public int getTotalAmountByFilter(ProductFilterDTO productFilterDTO) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Product> root = query.from(Product.class);

            List<Predicate> predicates = PredicateFormationAssistant.createFromDto(productFilterDTO, cb, root);
            if (!predicates.isEmpty()) {
                query.where(predicates.toArray(new Predicate[0]));
            }
            query.select(cb.count(root));
            return session.createQuery(query).getSingleResult().intValue();
        }
    }

    @Override
    public List<Product> findAllOffset(int initIndex) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Product", Product.class)
                    .setFirstResult(initIndex)
                    .setMaxResults(10)
                    .getResultList();
        }
    }


    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.createQuery("DELETE FROM OrderItem WHERE product.id = :productId")
                        .setParameter("productId", id)
                        .executeUpdate();
                Product mergedProduct = (Product) session.merge(product);
                session.delete(mergedProduct);
            }
            session.getTransaction().commit();
        }

    }
}

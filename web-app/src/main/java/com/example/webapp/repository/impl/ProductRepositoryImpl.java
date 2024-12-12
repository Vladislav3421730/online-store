package com.example.webapp.repository.impl;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.repository.ProductRepository;
import com.example.webapp.utils.HibernateUtils;
import com.example.webapp.utils.PredicateFormationAssistant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private static final ProductRepositoryImpl INSTANCE = new ProductRepositoryImpl();
    public static ProductRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Product> findAll() {
        return  HibernateUtils.getSessionFactory()
                .openSession()
                .createQuery("from Product p", Product.class)
                .getResultList();
    }

    @Override
    public List<Product> findAllByTitle(String title) {
        return HibernateUtils.getSessionFactory()
                .openSession()
                .createQuery(
                        "from Product p where lower(p.title) like lower(:title)", Product.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
    }

    @Override
    public List<Product> findAllByFilter(ProductFilterDTO productFilterDTO) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = PredicateFormationAssistant.createFromDto(productFilterDTO, cb, root);
        log.info("size {}", predicates.size());
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size() - 1]));
        }
        switch (productFilterDTO.getSort()) {
            case "cheap" -> query.orderBy(cb.asc(root.get("coast")));
            case "expensive" -> query.orderBy(cb.desc(root.get("coast")));
            case "alphabet" -> query.orderBy(cb.asc(root.get("title")));
        }
        return session.createQuery(query).getResultList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        return Optional.ofNullable(session.get(Product.class, id));
    }


    @Override
    @Transactional
    public void update(Product product) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(product);
        session.getTransaction().commit();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
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
        session.close();
    }


    @Override
    @Transactional
    public void save(Product product) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
    }
}

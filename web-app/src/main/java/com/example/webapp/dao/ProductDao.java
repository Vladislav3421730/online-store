package com.example.webapp.dao;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ProductDao implements DAO<Long, Product> {

    private final static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final ProductDao INSTANCE=new ProductDao();
    public static ProductDao getInstance(){
        return INSTANCE;
    }

    @Override
    public List<Product> findAll() {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        List<Product> products=session.createQuery("from Product p",Product.class).getResultList();
        session.getTransaction().commit();
        return products;
    }

    public List<Product> findAllByTitle(String title){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery<Product> query=cb.createQuery(Product.class);
        Root<Product> root=query.from(Product.class);
        query.select(root).where(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        List<Product> products = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return products;
    }

    public List<Product> findAllByFilter(ProductFilterDTO productFilterDTO){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery<Product> query=cb.createQuery(Product.class);
        Root<Product> root=query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if(productFilterDTO.getCategory()!=null &&
                !productFilterDTO.getCategory().isEmpty() && !productFilterDTO.getCategory().isBlank()){
            predicates.add(cb.like(cb.lower(root.get("category")),"%"+productFilterDTO.getCategory().toLowerCase()+"%"));
        }
        if(productFilterDTO.getMinPrice()!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("coast"),productFilterDTO.getMinPrice()));
        }
        if(productFilterDTO.getMaxPrice()!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("coast"),productFilterDTO.getMaxPrice()));
        }
        log.info("size {}",predicates.size());
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[predicates.size()-1]));
        }

        switch (productFilterDTO.getSort()){
            case "cheap"->query.orderBy(cb.desc(root.get("coast")));
            case "expensive"->query.orderBy(cb.asc(root.get("coast")));
            case "alphabet"->query.orderBy(cb.asc(root.get("title")));
        }

        List<Product> products = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        Product product=session.get(Product.class,id);
        session.getTransaction().commit();
        return Optional.ofNullable(product);
    }


    @Override
    @Transactional
    public void update(Product product) {
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(product);
        session.getTransaction().commit();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Product product = session.get(Product.class, id);
        if (product != null) {
            Product mergedProduct = (Product) session.merge(product);
            session.delete(mergedProduct);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @Transactional
    public void save(Product product) {
        Session session= sessionFactory.openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
    }
}

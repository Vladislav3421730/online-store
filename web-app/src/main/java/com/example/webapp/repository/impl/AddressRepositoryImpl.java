package com.example.webapp.repository.impl;


import com.example.webapp.model.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;


import com.example.webapp.repository.AddressRepository;
import com.example.webapp.utils.HibernateUtils;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressRepositoryImpl implements AddressRepository {

    private static final AddressRepositoryImpl INSTANCE = new AddressRepositoryImpl();
    public static AddressRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(Address address) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(address);
        session.getTransaction().commit();
    }
}

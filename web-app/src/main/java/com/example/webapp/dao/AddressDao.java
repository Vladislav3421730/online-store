package com.example.webapp.dao;


import com.example.webapp.model.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDao {

    private final static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final AddressDao INSTANCE=new AddressDao();
    public static AddressDao getInstance(){
        return INSTANCE;
    }

   public void save(Address address){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.save(address);
        session.getTransaction().commit();
   }
}

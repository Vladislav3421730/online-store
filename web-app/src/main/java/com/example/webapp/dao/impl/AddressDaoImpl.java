package com.example.webapp.dao.impl;

import com.example.webapp.model.Address;
import com.example.webapp.dao.AbstractHibernateDao;
import com.example.webapp.dao.AddressDao;

public class AddressDaoImpl extends AbstractHibernateDao<Address> implements AddressDao {

    private static final AddressDaoImpl INSTANCE = new AddressDaoImpl();

    public static AddressDaoImpl getInstance() {
        return INSTANCE;
    }

    private AddressDaoImpl() {
        super(Address.class);
    }


}

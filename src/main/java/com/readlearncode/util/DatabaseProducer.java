package com.readlearncode.util;


import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


public class DatabaseProducer {

    @Produces
    @PersistenceContext(unitName = "getting-started-with-enterprise-javabeans-persistence-unit", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
}

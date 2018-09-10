package com.sendfriend.models.data;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

     private static SessionFactory sessionFactory ;

     static {
         sessionFactory = new Configuration().configure().buildSessionFactory();
     }

     public static Session getSession() throws HibernateException {
         return sessionFactory.openSession();
     }

     public static Session getCurrentSession() throws HibernateException {
         return sessionFactory.getCurrentSession();
     }

}

package cn.wqp.crm.utils;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import cn.wqp.crm.domain.Customer;
import cn.wqp.crm.service.impl.CustomerServiceImpl;

/**
 * Hibernate 操作工具类
 * 
 */
public class HibernateUtils {
	private static Configuration configuration;
	private static SessionFactory sessionFactory;

	static {
		configuration = new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
	}

	public static Session openSession() {
		return sessionFactory.openSession();
	}

	public static void main(String[] args) {
		CustomerServiceImpl cs = new CustomerServiceImpl();
		List<Customer> findnoassociationCustomers = cs.findnoassociationCustomers();
		Customer c= cs.findCustomerByTelephone("1");
		System.out.println(c);
	}
}

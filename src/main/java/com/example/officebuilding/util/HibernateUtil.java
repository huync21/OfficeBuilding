package com.example.officebuilding.util;

import java.util.Properties;


import com.example.officebuilding.dtos.CompanyEmployeeDTO;
import com.example.officebuilding.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/qltoanha?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "Dontbetoohumble1234!");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(CompanyEmployeeEntity.class);
                configuration.addAnnotatedClass(CompanyEntity.class);
                configuration.addAnnotatedClass(BuildingEmployeeEntity.class);
                configuration.addAnnotatedClass(ContractEntity.class);
                configuration.addAnnotatedClass(FloorEntity.class);
                configuration.addAnnotatedClass(MonthEntity.class);
                configuration.addAnnotatedClass(MonthlyBillEntity.class);
                configuration.addAnnotatedClass(MonthlySalaryEntity.class);
                configuration.addAnnotatedClass(MonthlyServiceBillEntity.class);
                configuration.addAnnotatedClass(SalaryEntity.class);
                configuration.addAnnotatedClass(ServiceContractEntity.class);
                configuration.addAnnotatedClass(ServiceEntity.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

package com.example.officebuilding.dao;

import com.example.officebuilding.dtos.ServiceDTO;
import com.example.officebuilding.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ServiceDAO {
    public List<ServiceDTO> findAllUnregisterdServices(Integer companyId){
            Session session = null;
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT * FROM service " +
                    "WHERE id NOT IN " +
                    "(SELECT service_id FROM service_contract WHERE company_id = :companyId)");
            query.setParameter("companyId", companyId);
            List<ServiceDTO> list = query.list();
            session.getTransaction().commit();
            return list;
    }
}

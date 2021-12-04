package com.example.officebuilding.dao;

import com.example.officebuilding.dtos.ServiceDTO;
import com.example.officebuilding.entities.ServiceEntity;
import com.example.officebuilding.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ServiceDAO {
    @Autowired
    private ModelMapper modelMapper;
    public List<ServiceDTO> findAllUnregisterdServices(Integer companyId){
            Session session = null;
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT * FROM service " +
                    "WHERE id NOT IN " +
                    "(SELECT service_id FROM service_contract WHERE company_id = :companyId)")
                            .setResultTransformer( Transformers.aliasToBean(ServiceDTO.class));
            query.setParameter("companyId", companyId);
            List<ServiceDTO> list = query.list();
            session.getTransaction().commit();
            return list;
    }

    public List<ServiceDTO> findAllUnregisterdServices(Integer companyId,String serviceName){
        Session session = null;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("SELECT * FROM service " +
                "WHERE id NOT IN " +
                "(SELECT service_id FROM service_contract WHERE company_id = :companyId)" +
                "AND name LIKE :serviceName")
                .setResultTransformer( Transformers.aliasToBean(ServiceDTO.class));
        query.setParameter("companyId", companyId);
        query.setParameter("serviceName","%"+serviceName+"%");
        List<ServiceDTO> list = query.list();
        session.getTransaction().commit();
        return list;
    }
}

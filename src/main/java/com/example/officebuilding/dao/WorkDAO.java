package com.example.officebuilding.dao;

import com.example.officebuilding.dtos.WorkDTO;
import com.example.officebuilding.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public class WorkDAO {
    public void createNewWork(Integer empId, WorkDTO workDTO){
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            NativeQuery query = session.createSQLQuery("INSERT INTO work (title,detail,start_date,due_date,assigner,building_employee_id) VALUES(:title,:detail,:startDate,:dueDate,:assigner,:buildingEmployeeId)");
            query.setParameter("title",workDTO.getTitle());
            query.setParameter("detail",workDTO.getDetail());
            query.setParameter("startDate",workDTO.getStartDate());
            query.setParameter("dueDate",workDTO.getDueDate());
            query.setParameter("assigner",workDTO.getAssigner());
            query.setParameter("buildingEmployeeId",empId);
            System.out.println("-------------------------DAO------------>"+workDTO.getTitle()+" "+workDTO.getDetail()+" "+workDTO.getStartDate()+" "+workDTO.getDueDate()+" "+workDTO.getAssigner()+" "+empId);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){
            if(session.getTransaction() != null) session.getTransaction().rollback();
        }
    }

    public void updateWork(Integer workId, Integer empId, WorkDTO workDTO){
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            NativeQuery query = session.createSQLQuery("UPDATE work SET title = :title, detail = :detail,start_date = :startDate,due_date = :dueDate,assigner = :assigner ,building_employee_id = :buildingEmployeeId WHERE (id = :id)" );
            query.setParameter("id",workId);
            query.setParameter("title",workDTO.getTitle());
            query.setParameter("detail",workDTO.getDetail());
            query.setParameter("startDate",workDTO.getStartDate());
            query.setParameter("dueDate",workDTO.getDueDate());
            query.setParameter("assigner",workDTO.getAssigner());
            query.setParameter("buildingEmployeeId",empId);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch(Exception e){
            if(session.getTransaction() != null) session.getTransaction().rollback();

        }
    }
}

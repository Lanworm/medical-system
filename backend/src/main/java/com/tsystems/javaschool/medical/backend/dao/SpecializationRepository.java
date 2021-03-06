package com.tsystems.javaschool.medical.backend.dao;

import com.tsystems.javaschool.medical.backend.dto.SpecializationsDto;
import com.tsystems.javaschool.medical.backend.entities.SpecializationsEntity;
import com.tsystems.javaschool.medical.backend.entities.enums.IsDeleted;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

import static com.tsystems.javaschool.medical.backend.util.DateUtils.getCurrentTimestamp;

@Repository
public class SpecializationRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<SpecializationsEntity> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(SpecializationsEntity.class);
        criteria.addOrder(Order.asc("description"));
        criteria.add(Restrictions.eq("deleted", IsDeleted.N));

        List<SpecializationsEntity> specializationsEntities = criteria.list();

        return specializationsEntities;
    }

    @Transactional
    public void create(String description) {
        SpecializationsEntity specializationsEntity = new SpecializationsEntity();
        specializationsEntity.setDescription(description);
        specializationsEntity.setCreatedAt(getCurrentTimestamp());
        specializationsEntity.setUpdatedAt(getCurrentTimestamp());
        specializationsEntity.setDeleted(IsDeleted.N);
        Session session = sessionFactory.getCurrentSession();
        session.persist(specializationsEntity);
    }

    @Transactional
    public void delete(BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        SpecializationsEntity specializationsEntity = session.load(SpecializationsEntity.class, id);
        specializationsEntity.setDeleted(IsDeleted.Y);
        specializationsEntity.setUpdatedAt(getCurrentTimestamp());
        specializationsEntity.setId(id);
        session.update(specializationsEntity);
    }

    @Transactional
    public void update(SpecializationsDto params) {
        Session session = sessionFactory.getCurrentSession();
        SpecializationsEntity specializationsEntity = session.load(SpecializationsEntity.class, params.getId());
        specializationsEntity.setUpdatedAt(getCurrentTimestamp());
        specializationsEntity.setId(params.getId());
        specializationsEntity.setDescription(params.getDescription());
        session.update(specializationsEntity);
    }
}

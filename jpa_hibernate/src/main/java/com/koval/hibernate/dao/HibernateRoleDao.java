package com.koval.hibernate.dao;

import com.koval.hibernate.entities.RoleEntity;
import org.hibernate.Criteria;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HibernateRoleDao implements RoleDao {

    public GenericDao genericDao;

    public HibernateRoleDao() {
        this.genericDao = new GenericDao();
    }

    @Override
    public void create(RoleEntity roleEntity) {
        genericDao.genericOperation(session -> session.save(roleEntity));
    }

    @Override
    public void update(RoleEntity roleEntity) {
        genericDao.genericOperation(session -> session.update(roleEntity));
    }

    @Override
    public void remove(RoleEntity roleEntity) {
        genericDao.genericOperation(session -> session.delete(roleEntity));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RoleEntity> findAll() {
        return genericDao.genericRead(session -> {
            Criteria criteria = session.createCriteria(RoleEntity.class);
            return criteria.list();
        });
    }

    @Override
    public RoleEntity getRoleByName(String name) {
        return genericDao.genericRead(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);

            Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);
            criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));
            Query<RoleEntity> query = session.createQuery(criteriaQuery);

            return query.getSingleResult();
        });
    }
}
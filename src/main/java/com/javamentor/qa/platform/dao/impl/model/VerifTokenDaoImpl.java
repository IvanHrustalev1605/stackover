package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.VerifTokenDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.registration.VerificationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class VerifTokenDaoImpl extends ReadWriteDaoImpl<VerificationToken,Long> implements VerifTokenDao {
    @PersistenceContext
    private EntityManager entityManager;
}

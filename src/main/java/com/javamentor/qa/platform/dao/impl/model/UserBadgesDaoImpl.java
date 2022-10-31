package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserBadgesDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.user.UserBadges;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserBadgesDaoImpl extends ReadWriteDaoImpl<UserBadges, Long> implements UserBadgesDao {

    @PersistenceContext
    private EntityManager entityManager;
}

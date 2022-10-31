package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserFavoriteQuestionDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.user.UserFavoriteQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserFavoriteQuestionDaoImpl extends ReadWriteDaoImpl<UserFavoriteQuestion, Long> implements UserFavoriteQuestionDao {

    @PersistenceContext
    private EntityManager entityManager;
}

package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getByEmail(String email) {
        return super.getByEmail(email);
    }

    @Override
    public Optional<User> getByVerifCode(String verifCode) {
        return SingleResultUtil.getSingleResultOrNull(
                entityManager.createQuery("""
                                SELECT u
                                FROM User u
                                WHERE u.verifCode = :verifCode""", User.class)
                        .setParameter("verifCode", verifCode)
        );
    }
}

package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class IgnoredTagDaoImpl extends ReadWriteDaoImpl<IgnoredTag, Long> implements IgnoredTagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IgnoredTag> getIgnoredTagByUserId(Long userId) {
        return entityManager.createQuery("""
                        select it.ignoredTag
                        from IgnoredTag as it
                        where it.user.id = :userId
                        """, IgnoredTag.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<IgnoredTag> getByIdAndUser(Long tagId, User user) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT it FROM IgnoredTag it WHERE it.ignoredTag=:ignoredTag AND it.user=:user
                """,IgnoredTag.class)
                .setParameter("ignoredTag", tagId)
                .setParameter("user", user));
    }
}
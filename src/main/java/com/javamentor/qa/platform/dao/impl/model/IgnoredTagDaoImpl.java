package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IgnoredTagDaoImpl extends ReadWriteDaoImpl<IgnoredTag, Long> implements IgnoredTagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IgnoredTag> getIgnoredTagsByUserId(Long userId) {
        return entityManager
                .createQuery("""
                        SELECT tag.ignoredTag
                        FROM IgnoredTag tag
                        WHERE tag.user.id = :userId
                        """, IgnoredTag.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public boolean existsByTagIdAndUserId(Long tagId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT it
                        FROM IgnoredTag it
                        WHERE it.user.id = :userId AND it.ignoredTag.id = :tagId
                        """, IgnoredTag.class)
                .setParameter("userId", userId)
                .setParameter("tagId", tagId)).isPresent();
    }


}

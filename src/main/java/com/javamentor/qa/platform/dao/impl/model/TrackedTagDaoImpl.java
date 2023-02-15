package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TrackedTagDaoImpl extends ReadWriteDaoImpl<TrackedTag, Long> implements TrackedTagDao {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean existTrackedTadByUser(Long tagId, Long userId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT a FROM TrackedTag a
                        WHERE a.trackedTag.id = :tagId AND a.user.id = :userId
                        """, TrackedTag.class)
                .setParameter("tagId", tagId)
                .setParameter("userId", userId)).isPresent();
    }

}

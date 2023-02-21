package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
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
                        select new com.javamentor.qa.platform.models.entity.question.IgnoredTag(
                        it.ignoredTag,
                        it.user)
                        from IgnoredTag it where it.user.id =: userId
                        """, IgnoredTag.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}

package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class TrackedTagDaoImpl extends ReadWriteDaoImpl<TrackedTag, Long> implements TrackedTagDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TrackedTagDto> getListUserTrackedDto(User user) {
        Long userId = user.getId();
        return entityManager.createQuery("""
                SELECT 
                table_1.id,
                table_1.name
                FROM Tag table_1
                JOIN TrackedTag table_2 ON table_1.id = table_2.trackedTag.id
                WHERE table_2.user.id = :id
                GROUP BY table_1.id
                """, TrackedTagDto.class).setParameter("id", userId).getResultList();

    }
}
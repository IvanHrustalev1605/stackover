package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TrackedTagDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class TrackedTagDtoDaoImpl implements TrackedTagDtoDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<TrackedTagDto> getTrackedDtoByTrackedTagId(Long TrackedTagId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.TrackedTagDto(
                      t.id,
                      t.trackedTag.name
                      )
                FROM TrackedTag  t
                WHERE t.id = :TrackedTagId
                """,TrackedTagDto.class).setParameter("TrackedTagId", TrackedTagId));
    }
}

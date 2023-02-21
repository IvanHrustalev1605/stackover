package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TrackedTagDtoDao;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class TrackedTagDtoDaoImpl implements TrackedTagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<TrackedTagDto>> getAllByUserId(Long id) {
        return Optional.of(entityManager.createQuery("""
                        select new com.javamentor.qa.platform.models.dto.TrackedTagDto(
                        tt.id,
                        (select t.name from Tag t where t.id = tt.trackedTag.id))
                        from TrackedTag tt where tt.user.id =: id""", TrackedTagDto.class)
                .setParameter("id", id)
                .getResultList());
    }
}

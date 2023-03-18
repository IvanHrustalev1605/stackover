package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TagDto> getById(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.TagDto(
                      t.id,
                      t.name,
                      t.description)
                FROM Tag t
                WHERE t.id = :id
                """).setParameter("id", id));
    }
}

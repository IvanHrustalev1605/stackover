package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RelatedTagDto> getTopTags() {
        return entityManager.createQuery("""
                    SELECT
                    t.id AS id,
                    t.name AS title,
                    COALESCE(t.questions.size, 0L) AS countQuestion
                    FROM Tag t
                    GROUP BY t.id, t.name
                    ORDER BY countQuestion DESC
                    """, RelatedTagDto.class).setMaxResults(10).getResultList();
    }
}

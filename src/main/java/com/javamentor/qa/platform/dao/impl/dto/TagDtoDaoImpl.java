package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<RelatedTagDto>> getTopTags() {
        return Optional.ofNullable(entityManager.createQuery("""
                            SELECT NEW com.javamentor.qa.platform.models.dto.RelatedTagDto (
                            t.id,
                            t.name,
                            (select count(*) from t.questions t1 where t1.id in (select id from Question)))
                            FROM Tag as t
                            order by 3 desc
                """, RelatedTagDto.class).setMaxResults(10).getResultList());
    }

}

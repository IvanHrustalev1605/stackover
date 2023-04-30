package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TagDto> getTop3TagsByUserId(Long id) {
        TypedQuery<TagDto> query = entityManager.createQuery("""
                        SELECT new com.javamentor.qa.platform.models.dto.TagDto(
                        t.id,
                        t.name,
                        t.description)       
                        FROM Reputation as r 
                        join r.question as rq
                        join r.question.tags as t
                        where rq.user.id = :id 
                                and r.count IN (SELECT rp.count from Reputation rp where rp.author.id = :id ORDER BY rp.count desc ) 
                                and r.type = 3
                        GROUP BY t.id, t.name, t.description 
                        ORDER BY count (t.id) desc 
                        """, TagDto.class)
                .setMaxResults(3)
                .setParameter("id", id);
        return query.getResultList();
    }
}

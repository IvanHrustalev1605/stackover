package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> getTop3TagsByUserId(Long userId) {
        return entityManager.createQuery("""
                                                 select t
                                                 from Tag t
                                                 join t.questions q
                                                 join Reputation r on r.question = q
                                                 where r.sender.id = :userId
                                                 and r.type = com.javamentor.qa.platform.models.entity.user.reputation.ReputationType.VoteAnswer
                                                 group by t.id
                                                 order by sum(r.count) desc, t.name asc
                                                 """, Tag.class)
                            .setParameter("userId", userId)
                            .setMaxResults(3)
                            .getResultList();
    }

    @Override
    public List<RelatedTagDto> getTopTags() {
        return entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.RelatedTagDto(
                    tag.id,
                    tag.name,
                    COUNT(question.id))
                    FROM Tag tag
                    LEFT JOIN tag.questions question
                    GROUP BY tag.id, tag.name
                    ORDER BY COUNT(question.id) DESC""", RelatedTagDto.class)
                .setMaxResults(10)
                .getResultList();
    }
}

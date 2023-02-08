package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<QuestionDto> getById(Long questionId, Long authorizedUserId) {
        // cant get List<TagDto>
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                    SELECT NEW com.javamentor.qa.platform.models.dto.QuestionDto(
                        a.id,
                        a.title,
                        a.user.id,
                        a.user.fullName,
                        a.user.imageLink,
                        a.description,
                        (SELECT COUNT(qv) FROM QuestionViewed qv where qv.question.id = :questionId),
                        (SELECT COUNT(r.count) FROM Reputation r WHERE r.author.id = :userId),
                        (SELECT COUNT(an) FROM Answer an WHERE an.question.id = :questionId),
                        (SELECT COALESCE(SUM(CASE WHEN vq.vote = 'UP' THEN 1 WHEN vq.vote = 'DOWN' THEN -1 END), 0)
                                                                   FROM VoteQuestion vq WHERE vq.question.id = :questionId),
                        a.persistDateTime,
                        a.lastUpdateDateTime, 
                        (SELECT COUNT(vq) FROM VoteQuestion vq WHERE vq.question.id = :questionId),
                        (SELECT vq.vote FROM VoteQuestion vq WHERE vq.user.id = :userId),
                        (SELECT t FROM Tag t JOIN t.questions q WHERE q.id = :questiondId)
                    )
                    FROM Question a
                    WHERE a.id = :questionId
                    """, QuestionDto.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", authorizedUserId));
    }
}

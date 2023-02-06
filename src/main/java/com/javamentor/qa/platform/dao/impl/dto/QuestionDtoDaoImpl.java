package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadOnlyDaoImpl;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl extends ReadOnlyDaoImpl<Question, Long> implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<QuestionDto> getById(Long questionId, Long authorizedUserId) {
        VoteType vote = VoteType.UP;
        System.out.println(vote.name());

        return Optional.of(entityManager.createQuery("""
                    SELECT NEW com.javamentor.qa.platform.models.dto.QuestionDto(
                        a.id,
                        a.title,
                        a.user.id,
                        a.user.fullName,
                        a.user.imageLink,
                        a.description,
                        a.viewCount,
                        a.user.reputationCount,
                        (SELECT COUNT(an) FROM Answer an WHERE an.question.id = :questionId),
                        (SELECT COALESCE(SUM(CASE WHEN vq.vote = 'UP' THEN 1 WHEN vq.vote = 'DOWN' THEN -1 END), 0)
                                                                   FROM VoteQuestion vq WHERE vq.question.id = :questionId),            
                        a.persistDateTime,
                        a.lastUpdateDateTime, 
                        (SELECT COUNT(vq) FROM VoteQuestion vq WHERE vq.question.id = :questionId),
                        (SELECT vq.vote FROM VoteQuestion vq WHERE vq.user.id = :userId)
                    )
                    FROM Question a
                    WHERE a.id = :questionId
                    """, QuestionDto.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", authorizedUserId)
                .getSingleResult());
    }


}

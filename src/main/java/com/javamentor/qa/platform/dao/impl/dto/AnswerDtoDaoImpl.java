package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<List<AnswerDto>> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        return Optional.of(entityManager.createQuery("""
                    SELECT NEW com.javamentor.qa.platform.models.dto.question.answer.AnswerDto(
                        a.id,
                        a.user.id,
                        a.question.id,
                        a.htmlBody,
                        a.persistDateTime,
                        a.isHelpful,
                        a.dateAcceptTime,
                        (SELECT COALESCE(SUM(CASE WHEN va.voteType = 'UP' THEN 1 WHEN va.voteType = 'DOWN' THEN -1 END), 0)
                            FROM VoteAnswer va WHERE va.answer.question.id = :questionId),
                        (SELECT SUM(r.count) FROM Reputation r WHERE r.author.id = a.user.id),
                        a.user.imageLink,
                        a.user.nickname,
                        (SELECT COUNT(va) FROM VoteAnswer va WHERE va.answer.question.id = :questionId),
                        (SELECT va.voteType FROM VoteAnswer va WHERE va.answer.question.id = :questionId AND va.user.id = :userId)
                    )
                    FROM Answer a
                    WHERE a.question.id = :questionId AND a.question.isDeleted = false
                    """, AnswerDto.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", userId)
                .getResultList());
    }
}
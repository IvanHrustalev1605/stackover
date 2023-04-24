package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        return entityManager.createQuery("""
                        SELECT NEW com.javamentor.qa.platform.models.dto.AnswerDto(
                            a.id,
                            a.user.id,
                            a.question.id,
                            a.htmlBody,
                            a.persistDateTime,
                            a.isHelpful,
                            a.dateAcceptTime,
                            COALESCE(SUM(CASE WHEN va.voteType = 'UP' THEN 1 WHEN va.voteType = 'DOWN' THEN -1 END), 0),
                            COALESCE(ra.count, 0),
                            a.user.imageLink,
                            a.user.nickname,
                            COALESCE(count(va.id), 0),
                            COALESCE(va.voteType, 0)
                        )
                        FROM Answer a
                        LEFT JOIN VoteAnswer va ON a.id = va.answer.id
                        LEFT JOIN Reputation ra ON a.id = ra.answer.id
                        WHERE a.question.id = :questionId AND a.user.id = :userId AND a.question.isDeleted = false
                        GROUP BY a.id, a.user.id,  a.question.id, a.htmlBody, a.persistDateTime, a.isHelpful, a.dateAcceptTime, ra.count, a.user.imageLink, a.user.nickname, va.voteType
                        """, AnswerDto.class)
                .setParameter("questionId", questionId)
                .setParameter("userId", userId)
                .getResultList();
    }

}
package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import java.util.List;

@Repository
public class AnswerDtoDaoImpl  implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<AnswerDto> getAllAnswerDtoQuestionId(Long userId, Long questionId) {
        return entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.AnswerDto (
                a.id,
                e.htmlBody,
                e.persistDateTime,
                e.dateAcceptTime,
                (SELECT COALESCE(SUM(r.count), 0) FROM Reputation r WHERE r.answer.id = e.id),
                (SELECT COALESCE(SUM(r.count), 0) FROM Reputation r WHERE r.author.id = a.id),
                a.imageLink,
                a.nickname,
                e.voteAnswers,
                e.id,
                e.question,
                (SELECT COUNT(DISTINCT v) + COUNT(DISTINCT q)
                FROM VoteAnswer v, VoteQuestion q
                WHERE v.user.id = a.id AND q.user.id = a.id
                ))
                FROM User a, Answer e
                JOIN Reputation o ON a.id =o.author.id
                JOIN Reputation i ON e.id =i.answer.id
                WHERE e.id IN :ids""", AnswerDto.class).
                setParameter("ids", userId)
                .setParameter("ids", questionId)
                .getResultList();

    }


}


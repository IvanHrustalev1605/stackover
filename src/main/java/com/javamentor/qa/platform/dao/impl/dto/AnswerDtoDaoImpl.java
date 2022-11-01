package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerDto> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        return entityManager.createQuery("""
                SELECT a.id AS answerId, a.user.id as userID,  a.question.id AS questionId, 
                a.htmlBody AS body, a.persistDateTime AS persistDate,a.isHelpful AS isHelpful,
                a.dateAcceptTime AS dateAccept, COALESCE(SUM(va.voteType), 0) as countValuable,
                ru.count as countUserReputation, User.imageLink AS image, User.nickname AS nickname,
                COALESCE(count(va.voteType), 0) as countVoit, 
                COALESCE(CASE WHEN SUM(va.voteType)<0 THEN -1 ELSE 1 END, 0) AS voteType
                FROM Answer a
                LEFT JOIN VoteAnswer va ON a.id = va.answer.id
                LEFT JOIN Reputation ra ON a.id = ra.answer.id
                LEFT JOIN Reputation ru ON :userId = ru.sender.id
                WHERE a.question.id = :questionId and a.user.id = :userId
                GROUP BY a.id, a.user.id,  a.question.id, a.htmlBody, a.persistDateTime,a.isHelpful, 
                a.dateAcceptTime,ra.count, ru.count, User.imageLink, User.nickname, va.voteType
                """)
                .setParameter("questionId", questionId)
                .setParameter("userId", userId)
                .getResultList();
    }
}
package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDto;
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
    @Transactional(readOnly = true)
    public Optional<List<AnswerDto>> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {

        return Optional.ofNullable(entityManager.createQuery("""
                SELECT NEW com.javamentor.qa.platform.models.dto.AnswerDto (a.id, a.user.id, a.question.id, a.htmlBody,
                a.persistDateTime,a.isHelpful, a.dateAcceptTime,
                (select coalesce(sum(case when voteType = 'UP'  then  1 when voteType='DOWN' then -1 end),0) from VoteAnswer where answer.id = a.id),
                (select sum(coalesce(r.count, 0) ) from Reputation as r where a.user.id = r.author.id),
                a.user.imageLink, a.user.nickname,
                (select count (*) from VoteAnswer as v where v.answer.id =a.id),
                (select v.voteType from VoteAnswer as v where user.id = :userId))
                                FROM Answer as a
                                where a.question.id = :questionId and a.user.id = :userId
                                """, AnswerDto.class)
                .setParameter("userId", userId).setParameter("questionId", questionId).getResultList());
    }

}
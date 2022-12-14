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

    @Override
    public List<AnswerDto> getAllAnswerDtoQuestionId(Long userId, Long questionId) {
        return entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.AnswerDto(
                ans.id,
                ans.user.id,
                ans.question.id,
                ans.htmlBody,
                ans.persistDateTime,
                ans.isHelpful,
                ans.dateAcceptTime,
                coalesce(sum(case when va.voteType = 'UP' then 1 when va.voteType = 'DOWN' then -1 end), 0),
                coalesce(sum(ur.count), 0),
                ans.user.imageLink,
                ans.user.nickname,
                coalesce(count (va.id), 0),
                coalesce(va.voteType, null)
                from Answer ans
                        left join VoteAnswer va on ans.id = va.answer.id
                        left join Reputation rep on ans.id = rep.answer.id
                        left join Reputation ur on :userId = ur.sender.id
                where ans.question.id = :questionId and ans.user.id = :userId and ans.isDeleted = FALSE
                group by ans.id, ans.user.id, ans.question.id, ans.htmlBody, ans.persistDateTime, ans.isHelpful,
                ans.dateAcceptTime, rep.count, ur.count, ans.user.imageLink, ans.user.nickname, va.voteType
                """, AnswerDto.class)
                .setParameter("userId", userId)
                .setParameter("questionId", questionId)
                .getResultList();
    }
}
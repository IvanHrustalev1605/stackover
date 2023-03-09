package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AnswerDtoDaoImpl extends ReadWriteDaoImpl<AnswerDto, Long> implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<AnswerDto>> getAllAnswersDtoByQuestionId(Long questionId, Long userId) {
        if ((questionId != null) && (userId != null)) {
            return Optional.ofNullable(entityManager.createQuery
                            ("SELECT NEW com.javamentor.qa.platform.models.dto.AnswerDto (a.id, a.user.id, a.question.id, a.htmlBody, " +
                                    "a.persistDateTime,a.isHelpful, a.dateAcceptTime," +
                                    "(select sum(case when voteType ='UP' then  1 when voteType='DOWN' then -1 end) from VoteAnswer where answer.id = a.id)," +
                                    "(select sum(r.count) from Reputation as r where a.user.id = r.author.id)," +
                                    "a.user.imageLink, a.user.nickname," +
                                    "(select count (*) from VoteAnswer as v where v.answer.id =a.id)," +
                                    "(select voteType from VoteAnswer where user.id = :userId)) " +
                                    "FROM Answer as a where a.question.id = :questionId and a.user.id = :userId ", AnswerDto.class)
                    .setParameter("userId", userId).setParameter("questionId", questionId).getResultList());
        }
        return Optional.empty();
    }

}
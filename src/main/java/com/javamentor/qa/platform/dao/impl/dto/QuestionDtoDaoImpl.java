package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<QuestionDtoDao> getById(long questionId, long authorizedUserId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        "select q.id, " +
                                "q.title, " +
                                "q.description,  " +
                                "q.lastUpdateDateTime,  " +
                                "q.persistDateTime,  " +
                                "u.id,  " +
                                "u.fullName,  " +
                                "u.imageLink, " +
                                "coalesce((select sum(r.count) from Reputation r where r.author.id = u.id), 0), " +
                                "(select count(qv.id) from QuestionViewed qv where qv.question.id = q.id), " +
                                "(select count(a.id) from Answer a where a.question.id = q.id), " +
                                "(select quv.vote from VoteQuestion quv where quv.question.id = q.id and quv.user.id =:userId), " +
                                "(select count(b) from BookMarks b where b.question.id = :questionId and b.user.id = :userId), " +
                                "(select (case when count(a) > 0 then true else false end) from Answer a where a.question.id = :questionId and a.user.id = :userId)" +
                                "from Question  q " +
                                "LEFT join q.user u " +
                                "where q.id =:questionId")

                .setParameter("questionId", questionId)
                .setParameter("userId", authorizedUserId));
    }
}

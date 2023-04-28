package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentDtoDaoImpl implements CommentDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionCommentDto> getAllQuestionCommentDtoById(Long questionId) {
        return entityManager.createQuery("""
                        SELECT new com.javamentor.qa.platform.models.dto.QuestionCommentDto(
                        q.comment.id,
                        q.question.id,
                        q.comment.lastUpdateDateTime,
                        q.comment.persistDateTime,
                        q.comment.text,
                        q.comment.user.id,
                        q.comment.user.imageLink,
                        (SELECT COALESCE(SUM(r.count), 0)
                            FROM  Reputation r
                            WHERE r.question.id = :questionId))
                        FROM CommentQuestion q
                        WHERE q.question.id = :questionId
                        """, QuestionCommentDto.class)
                .setParameter("questionId", questionId)
                .getResultList();
    }
}
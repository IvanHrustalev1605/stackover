package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDtoDaoImpl implements CommentDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<QuestionCommentDto>> getAllQuestionCommentDtoById(Long questionId) {
        return Optional.of(entityManager.createQuery("""
                    SELECT NEW com.javamentor.qa.platform.models.dto.QuestionCommentDto(
                        c.id,
                        c.question.id,
                        c.comment.lastUpdateDateTime,
                        c.comment.persistDateTime,
                        c.comment.text,
                        c.comment.user.id,
                        c.comment.user.imageLink,
                        (SELECT SUM(r.count) FROM Reputation r WHERE r.author.id = c.comment.user.id)
                    )
                    FROM CommentQuestion c
                    WHERE c.question.id = :questionId AND c.question.isDeleted = false
                    """, QuestionCommentDto.class)
                .setParameter("questionId", questionId)
                .getResultList());
    }
}

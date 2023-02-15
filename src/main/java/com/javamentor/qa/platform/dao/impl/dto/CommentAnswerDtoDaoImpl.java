package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentAnswerDtoDao;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentAnswerDtoDaoImpl implements CommentAnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CommentAnswerDto getCommentAnswerDto(Long commentAnswerId) {
        return entityManager
                .createQuery("""
                                     select new com.javamentor.qa.platform.models.dto.CommentAnswerDto(
                                         ca.comment.id,
                                         ca.answer.id,
                                         ca.comment.lastUpdateDateTime,
                                         ca.comment.persistDateTime,
                                         ca.comment.text,
                                         ca.comment.user.id,
                                         ca.comment.user.imageLink,
                                         (select sum(r.count) from Reputation r where r.author = ca.comment.user))
                                     from CommentAnswer ca
                                     where ca.comment.id = :commentAnswerId
                                     """, CommentAnswerDto.class)
                .setParameter("commentAnswerId", commentAnswerId)
                .getSingleResult();
    }
}

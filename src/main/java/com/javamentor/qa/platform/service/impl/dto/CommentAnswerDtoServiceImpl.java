package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentAnswerDtoDao;
import com.javamentor.qa.platform.exception.AnswerException;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentAnswerDtoServiceImpl implements CommentAnswerDtoService {
    private final CommentAnswerService commentAnswerService;
    private final CommentAnswerDtoDao commentAnswerDtoDao;
    private final AnswerService answerService;



    @Override
    @Transactional
    public Long addComment(User user, Long answerId, String text) {
        Answer answer = answerService
                .getById(answerId)
                .orElseThrow(() -> new AnswerException("Ответа не существует"));

        Comment comment = new Comment(CommentType.ANSWER);
        comment.setUser(user);
        comment.setText(text);

        CommentAnswer commentAnswer = new CommentAnswer();
        commentAnswer.setComment(comment);
        commentAnswer.setAnswer(answer);

        commentAnswerService.persist(commentAnswer);

        return commentAnswer.getId();
    }

    @Override
    public CommentAnswerDto getCommentAnswerDto(Long commentAnswerId) {
        return commentAnswerDtoDao.getCommentAnswerDto(commentAnswerId);
    }
}

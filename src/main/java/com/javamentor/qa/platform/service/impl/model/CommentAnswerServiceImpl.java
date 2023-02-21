package com.javamentor.qa.platform.service.impl.model;
import com.javamentor.qa.platform.dao.abstracts.model.CommentAnswerDao;
import com.javamentor.qa.platform.exception.AnswerException;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentAnswerServiceImpl extends ReadWriteServiceImpl<CommentAnswer, Long> implements CommentAnswerService {

    private final CommentAnswerDao commentAnswerDao;
    private final AnswerService answerService;

    public CommentAnswerServiceImpl(CommentAnswerDao commentAnswerDao, AnswerService answerService) {
        super(commentAnswerDao);
        this.commentAnswerDao = commentAnswerDao;
        this.answerService = answerService;
    }

    @Override
    public void addCommentToAnswer(User user, Long answerId, String text) {
        Answer answer = answerService
                .getById(answerId)
                .orElseThrow(() -> new AnswerException("Ответа не существует"));

        CommentAnswer commentAnswer = new CommentAnswer(text, user);
        commentAnswer.setAnswer(answer);
        commentAnswerDao.persist(commentAnswer);

    }

}
package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.CommentAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.AnswerException;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CommentAnswerServiceImpl extends ReadWriteServiceImpl<CommentAnswer, Long> implements CommentAnswerService {

    private final CommentAnswerDao commentAnswerDao;
    private final AnswerService answerService;

    public CommentAnswerServiceImpl(ReadWriteDao<CommentAnswer, Long> readWriteDao, CommentAnswerDao commentAnswerDao, AnswerService answerService) {

        super(readWriteDao);
        this.commentAnswerDao = commentAnswerDao;
        this.answerService = answerService;

    }

    @Override
    @Transactional
    public void addCommentToAnswer(User user, Long answerId, String comment) {
        /*Достаем нужный ответ*/
        Optional<Answer> answer = answerService.getById(answerId);

        /*Выбрасываем исключение, если ответ не найден*/
        if (answer.isEmpty()) {
            throw new AnswerException("Answer not found!");
        }

        /*Создаем новый объект CommentAnswer, который содержит свойства: найденный ответ,
        переданный текст комментария, объект пользователя, новый объект комментария*/
        CommentAnswer commentAnswer = new CommentAnswer();
        commentAnswer.setAnswer(answer.get());
        commentAnswer.setText(comment);
        commentAnswer.setUser(user);
        commentAnswer.setComment(new Comment());

        /*Добавляем новый комментарий в базу данных*/
        commentAnswerDao.persist(commentAnswer);
    }
}

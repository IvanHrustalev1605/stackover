package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerServiceImpl extends ReadWriteServiceImpl<Answer, Long> implements AnswerService {

    private final AnswerDao answerDao;

    public AnswerServiceImpl(ReadWriteDao<Answer, Long> readWriteDao, AnswerDao answerDao) {
        super(readWriteDao);
        this.answerDao = answerDao;
    }

    @Override
    public Optional<Answer> getAnswerByIdAndUserId(Long answerId, Long userId) {
        return answerDao.getAnswerByIdAndUserId(answerId, userId);
    }

    @Override
    public void markAnswerAsDeletedById(Long id) throws NotFoundException {
       if (answerDao.getById(id).isEmpty()) {
           throw new NotFoundException(String.format("The answer with id = %s does not exist", id));
       } else {
           answerDao.getById(id).ifPresent(x -> {
               if (!x.getIsDeleted()) {
                   x.setIsDeleted(true);
                   answerDao.update(x);
               }
           });
       }
    }
}

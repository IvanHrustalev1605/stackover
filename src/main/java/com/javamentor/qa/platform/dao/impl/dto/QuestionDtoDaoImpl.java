package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.converters.QuestionDtoConverter;
import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<QuestionDto> getById(Long questionId, Long authorizedUserId) {
//        Question question = entityManager.find(Question.class, questionId);
        Question question = new Question();
        question.setId(1l);
        question.setTitle("Question 1");
        question.setDescription("Some desc");
        return Optional.ofNullable(QuestionDtoConverter.INSTANCE.toQuestionDto(question));
    }

}

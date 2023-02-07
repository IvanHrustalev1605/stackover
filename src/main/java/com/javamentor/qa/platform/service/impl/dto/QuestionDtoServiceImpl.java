package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.converters.QuestionConverter;
import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionDtoServiceImpl implements QuestionDtoService {
    private final TagDtoService tagDtoService;
    private final QuestionService questionService;


    @Override
    public QuestionDto addQuestion(QuestionCreateDto questionCreateDto, User user) {


        if (questionCreateDto.getTitle().isEmpty()) {
            throw new RuntimeException("title cant be empty or null");
        }
        if (questionCreateDto.getDescription().isEmpty()) {
            throw new RuntimeException("Description cant be empty or null");
        }
        if (questionCreateDto.getTags().isEmpty())  {
            throw new RuntimeException("Tags cant be empty or null");
        }


        Question question = QuestionConverter.INSTANCE.questionCreateDtoToQuestion(questionCreateDto);
        question.setUser(user);
        question.setTags(tagDtoService.checkTags(questionCreateDto.getTags()));
        questionService.persist(question);
        return QuestionConverter.INSTANCE.questionToQuestionDto(question);
    }

}

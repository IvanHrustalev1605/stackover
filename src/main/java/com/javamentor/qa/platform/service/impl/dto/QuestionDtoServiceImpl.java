package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.mapper.QuestionMapper;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuestionDtoServiceImpl implements QuestionDtoService {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final TagService tagService;

    @Override
    @Transactional
    public QuestionDto createQuestion(QuestionCreateDto questionCreateDto, User user) {
        Question question = questionMapper.questionCreateDtoToQuestion(questionCreateDto);
        question.setUser(user);
        question.setTags(tagService.tags(question.getTags()));
        questionService.persist(question);
        question = questionService.getById(question.getId()).get();

        return questionMapper.questionToQuestionDto(question);
    }
}

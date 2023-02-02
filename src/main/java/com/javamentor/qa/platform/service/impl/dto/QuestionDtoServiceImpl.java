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
        //TODO
        //валидация tittle empty\null+
        //валидация description empty\null+
        //валидация tags empty\null + проверка\добавление тэга в БД+

        if (Objects.equals(questionCreateDto.getTitle(), "") | questionCreateDto.getTitle() == null) {
            throw new RuntimeException("title cant be empty or null");
        }
        if (Objects.equals(questionCreateDto.getDescription(), "") | questionCreateDto.getDescription() == null) {
            throw new RuntimeException("Description cant be empty or null");
        }
        if (Objects.equals(questionCreateDto.getTags(), "") | questionCreateDto.getTags() == null) {
            throw new RuntimeException("Tags cant be empty or null");
        }

    // получаю QuestionCreateDto и Юзера, надо создать Question,  получить QuestionDto+

        Question question = new Question(); //создали сущность
        question.setTitle(questionCreateDto.getTitle()); //присвоили поля из questionCreateDto
        question.setDescription(questionCreateDto.getDescription());
        question.setUser(user);
        question.setTags(tagDtoService.checkTags(questionCreateDto.getTags()));
        questionService.persist(question);

        return QuestionConverter.INSTANCE.questionToQuestionDto(question); //маппим question в QuestionDto и возвращаем
    }

}

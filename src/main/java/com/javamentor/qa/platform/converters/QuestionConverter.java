package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public abstract class QuestionConverter {
    public static QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class);

    protected QuestionConverter() {
    }

    public Question questionConverterCreateToQuestion(QuestionCreateDto questionCreateDto) {
        Question question = new Question();
        question.setTitle(questionCreateDto.getTitle());
        question.setDescription(questionCreateDto.getDescription());
        return question;
    }
}

package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionDtoConverter {
    QuestionDtoConverter INSTANCE = Mappers.getMapper(QuestionDtoConverter.class);
    QuestionDto toQuestionDto(Question question);
}


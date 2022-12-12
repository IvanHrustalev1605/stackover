package com.javamentor.qa.platform.models.mapper;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);
    Question toQuestionCreate(QuestionCreateDto dto);
    Question toQuestion(QuestionDto dto);
    QuestionDto toQuestionDto(Question entity);
}

package com.javamentor.qa.platform.mapper;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionCreateDtoToQuestion(QuestionCreateDto questionCreateDto);

    @Mapping(source = "question.tags", target = "listTagDto")
    @Mapping(source = "question.user.id", target = "authorId")
    @Mapping(source = "question.user.fullName", target = "authorName")
    QuestionDto questionToQuestionDto(Question question);

}
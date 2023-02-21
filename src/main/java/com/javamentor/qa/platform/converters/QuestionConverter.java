package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionConverter {

    QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class );
    //TODO разкоментить @Mapping(source = "tags", target = "listTagDto")
    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.fullName", target = "authorName")
    QuestionDto questionToQuestionDto(Question question);


    QuestionCreateDto questionToQuestionCreateDto(Question question);

    Question questionCreateDtoToQuestion(QuestionCreateDto questionCreateDto);

}

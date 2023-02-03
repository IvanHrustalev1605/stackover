package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionConverter {

    QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class );
    @Mapping(source = "tags", target = "listTagDto")
    QuestionDto questionToQuestionDto (Question question);


}

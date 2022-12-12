package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@Schema(description = "Converter QuestionCreateDto -> Question <-> QuestionDto")
public abstract class QuestionConverter {

    public static QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class);

    @Mappings(value = {@Mapping(source = "authorId", target = "user.id"),
            @Mapping(source = "authorName", target = "user.fullName"),
            @Mapping(source = "listTagDto", target = "tags")})
    public abstract Question questionDtoToEntity(QuestionDto questionDto);

    @InheritInverseConfiguration
    public abstract QuestionDto entityToQuestionDto(Question question);

    @InheritInverseConfiguration
    public abstract Question questionConverterCreateToQuestion(QuestionCreateDto questionCreateDto);
}

package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagConverter {

    TagConverter INSTANCE = Mappers.getMapper(TagConverter.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    TagDto tagToTagDto(Tag tag);

}

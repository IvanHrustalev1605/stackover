package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IgnoredTagConverter {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "ignoredTag.name", target = "name")
    IgnoredTagDto ignoredTagToIgnoredTagDto(IgnoredTag ignoredTag);
}

package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class IgnoredTagConverter {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "ignoredTag.name", target = "name")
    public abstract IgnoredTagDto IgnoredTagToIgnoredTagDto(IgnoredTag ignoredTag);
}

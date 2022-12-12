package com.javamentor.qa.platform.models.mapper;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TagListMapper {
    TagListMapper INSTANCE = Mappers.getMapper(TagListMapper.class);
    ArrayList<Tag> toTagArrayList(ArrayList<TagDto> dto);
    ArrayList<TagDto> toTagArrayListDto(ArrayList<Tag> entity);
    List<Tag> toTagList(List<TagDto> tagDtoList);
    List<TagDto> toTagListDto(List<Tag> tagList);
}

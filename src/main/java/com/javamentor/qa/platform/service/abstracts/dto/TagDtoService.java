package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDtoService {

    List<Tag> checkTags(List<TagDto> tagDtoList);

    Optional<TagDto> getById(Long tagId);

}

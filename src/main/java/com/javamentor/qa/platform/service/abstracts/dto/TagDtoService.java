package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;

import java.util.List;
import java.util.Optional;

public interface TagDtoService {
    Optional<List<RelatedTagDto>> getTopTags();

    Optional<TagDto> getById(Long id);
}

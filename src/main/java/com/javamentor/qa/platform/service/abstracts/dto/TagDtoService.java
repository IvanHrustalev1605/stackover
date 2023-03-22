package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;

import java.util.List;
import java.util.Optional;

import com.javamentor.qa.platform.models.dto.TagDto;

import java.util.Optional;

public interface TagDtoService {
    Optional<List<RelatedTagDto>> getTopTags();

    public Optional<TagDto> getById(Long id);
}

package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagDto;

import java.util.Optional;

public interface TagDtoService {

    public Optional<TagDto> getById(Long id);
}

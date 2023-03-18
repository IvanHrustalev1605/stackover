package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagDto;

import java.util.Optional;

public interface TagDtoDao {

    public Optional<TagDto> getById(Long id);
}

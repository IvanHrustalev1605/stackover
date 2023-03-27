package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;

import java.util.Optional;
import java.util.List;

public interface TagDtoDao {

    public Optional<TagDto> getById(Long id);
    Optional<List<RelatedTagDto>> getTopTags();
    Optional<List<TagDto>> getTop3TagsByUserId(Long id);
}

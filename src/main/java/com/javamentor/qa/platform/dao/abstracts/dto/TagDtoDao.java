package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;

import java.util.List;
import java.util.Optional;

public interface TagDtoDao {
    Optional<List<RelatedTagDto>> getTopTags();
}

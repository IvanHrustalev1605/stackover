package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;

import java.util.List;

public interface TagDtoDao {
    List<RelatedTagDto> getTopTags();
    List<TagDto> getTop3TagsByUserId(Long id);
}

package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.List;

public interface TagDtoDao {
    List<Tag> getTop3TagsByUserId(Long userId);
    List<RelatedTagDto> getTopTags();
}

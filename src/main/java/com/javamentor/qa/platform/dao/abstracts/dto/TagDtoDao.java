package com.javamentor.qa.platform.dao.abstracts.dto;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;

import java.util.List;

public interface TagDtoDao {
    List<IgnoredTagDto> getAllIgnoredTags(Long userId);
    List<RelatedTagDto> getTopTags();
}

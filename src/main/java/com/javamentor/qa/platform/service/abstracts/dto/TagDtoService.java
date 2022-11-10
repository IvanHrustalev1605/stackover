package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;

import java.util.List;

public interface TagDtoService {
    List<RelatedTagDto> getTopTags();

    List<IgnoredTagDto> getAllIgnoredTags(Long userId);


}

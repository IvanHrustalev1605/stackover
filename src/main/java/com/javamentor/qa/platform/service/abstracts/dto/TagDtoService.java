package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;

import java.util.List;

public interface TagDtoService {
    List<RelatedTagDto> getTopTags();
    List<TagDto> getTop3TagsByUserId(Long id);

}

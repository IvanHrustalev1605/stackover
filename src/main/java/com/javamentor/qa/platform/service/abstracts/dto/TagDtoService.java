package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;

import java.util.List;

public interface TagDtoService {
    List<RelatedTagDto> getTopTags();

}

package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.List;

public interface TagDtoService {

    List<Tag> checkTags(List<TagDto> tagDtoList);

    List<TagDto> getTop3TagsByUserId(Long userId);

    List<RelatedTagDto> getTenTopTags();

}

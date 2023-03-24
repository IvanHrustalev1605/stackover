package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;

import java.util.List;

public interface IgnoredTagDtoService {
    List<IgnoredTagDto> getIgnoredTagsByUserId(Long userId);
}

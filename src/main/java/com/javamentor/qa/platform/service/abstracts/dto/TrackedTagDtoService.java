package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TrackedTagDto;

import java.util.List;

public interface TrackedTagDtoService {
    List<TrackedTagDto> getAllByUserId(Long id);
}
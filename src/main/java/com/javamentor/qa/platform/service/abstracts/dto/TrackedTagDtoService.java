package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TrackedTagDto;

import java.util.Optional;

public interface TrackedTagDtoService {

    Optional<TrackedTagDto> getTrackedDtoByTagId(Long TagId);

}


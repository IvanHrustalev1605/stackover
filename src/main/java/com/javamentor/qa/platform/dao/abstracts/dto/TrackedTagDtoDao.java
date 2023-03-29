package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TrackedTagDto;

import java.util.Optional;

public interface TrackedTagDtoDao {
    Optional<TrackedTagDto> getTrackedDtoByTrackedTagId(Long TrackedTagId);
}

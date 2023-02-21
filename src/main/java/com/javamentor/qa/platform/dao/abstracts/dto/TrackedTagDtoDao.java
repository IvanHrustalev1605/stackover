package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TrackedTagDto;

import java.util.List;
import java.util.Optional;

public interface TrackedTagDtoDao {

    Optional<List<TrackedTagDto>> getAllByUserId(Long id);
}

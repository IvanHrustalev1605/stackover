package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.TrackedTagDto;

import java.util.List;

public interface TrackedTagDtoDao {

    List<TrackedTagDto> getAllByUserId(Long id);
}

package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.IgnoredTagDto;

import java.util.List;

public interface IgnoredTagDtoDao {

    List<IgnoredTagDto> getAllByUserId(Long id);
}

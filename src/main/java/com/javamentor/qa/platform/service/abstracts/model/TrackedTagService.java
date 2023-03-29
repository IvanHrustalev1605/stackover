package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.List;
import java.util.Optional;


public interface TrackedTagService extends ReadWriteService<TrackedTag, Long> {
    List<TrackedTagDto> getListUserTrackedDto(User user);

    void addTagToTrackedTag(Long tagId,User user);
    Optional<Long> getTrackedTagIdByTagId(Long TagId);
}

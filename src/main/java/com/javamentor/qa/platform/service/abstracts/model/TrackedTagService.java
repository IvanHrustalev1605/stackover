package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface TrackedTagService extends ReadWriteService<TrackedTag, Long> {

    boolean existTrackedTadByUser(Long tagId, Long userId);

    void saveTrackedTag(TrackedTag trackedTag);

}

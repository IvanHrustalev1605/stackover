package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;


public interface TrackedTagDao extends ReadWriteDao<TrackedTag, Long> {
    List<TrackedTagDto> getListUserTrackedDto(User user);
}

package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;

import java.util.Optional;

public interface IgnoredTagDao extends ReadWriteDao<IgnoredTag, Long> {

    Optional<IgnoredTag> getIgnoredTagByTagIdAndUserId(Long tagId, Long userId);

}

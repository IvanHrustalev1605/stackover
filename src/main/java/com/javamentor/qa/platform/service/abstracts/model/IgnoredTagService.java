package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.exception.TagAlreadyExistsException;
import com.javamentor.qa.platform.exception.TagNotFoundException;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface IgnoredTagService extends ReadWriteService<IgnoredTag, Long> {
    boolean existsByTagIdAndUserId(Long tagId, Long userId);
    TagDto addIgnoredTag(Long tagId, User user) throws TagNotFoundException, TagAlreadyExistsException;
}

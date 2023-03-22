package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.Optional;

public interface IgnoredTagService extends ReadWriteService<IgnoredTag, Long> {

    public Optional<TagDto> addTagToIgnoreList(Long tagId, User user);

    public Optional<IgnoredTag> getByIdAndUser(Long tagId, User user);
}

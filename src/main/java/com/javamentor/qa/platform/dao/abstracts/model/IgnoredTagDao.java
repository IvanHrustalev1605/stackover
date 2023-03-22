package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public interface IgnoredTagDao extends ReadWriteDao<IgnoredTag, Long> {

    public Optional<IgnoredTag> getByIdAndUser(Long tagId, User user);

}

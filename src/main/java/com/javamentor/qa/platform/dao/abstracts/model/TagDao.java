package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends ReadWriteDao<Tag, Long> {
    List<Tag> backListTag(List<String> names);

}

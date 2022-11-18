package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

import java.util.List;

import java.util.Optional;

public interface TagService extends ReadWriteService<Tag, Long> {
    List<Tag> backListTag(List<String> name);

    @Override
    Optional<Tag> getById(Long id);

}

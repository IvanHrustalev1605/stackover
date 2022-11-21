package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class IgnoredTagServiceImpl extends ReadWriteServiceImpl<IgnoredTag, Long> implements IgnoredTagService {
    private final TagService tagService;
    private final ReadWriteDao readWriteDao;
    public IgnoredTagServiceImpl(ReadWriteDao<IgnoredTag, Long> readWriteDao, TagService tagService) {
        super(readWriteDao);
        this.tagService = tagService;
        this.readWriteDao = readWriteDao;
    }

    @Override
    public void addTag(Long tagId) {
        readWriteDao.persist(tagService.getById(tagId));
    }
}

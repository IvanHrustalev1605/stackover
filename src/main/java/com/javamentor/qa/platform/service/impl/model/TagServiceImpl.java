package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(ReadWriteDao<Tag, Long> readWriteDao, TagDao tagDao) {
        super(readWriteDao);
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> backListTag(List<String> names) {
        return tagDao.backListTag(names);
    }

}

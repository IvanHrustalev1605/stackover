package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {
    private TagDao tagDao;

    public TagServiceImpl(ReadWriteDao<Tag, Long> readWriteDao, TagDao tagDao) {
        super(readWriteDao);
        this.tagDao = tagDao;
    }

    @Transactional
    @Override
    public Optional<Tag> getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    @Transactional
    public List<Tag> tags(List<Tag> tags) {
        List<Tag> t = new ArrayList<>();
        for (Tag tag : tags) {
            Optional<Tag> tagOptional = tagDao.getTagByName(tag.getName());
            if (tagOptional.isEmpty()) {
                tag.setDescription("default_tag");
                tagDao.persist(tag);
                t.add(tagDao.getTagByName(tag.getName()).get());
            } else {
                t.add(tagDao.getTagByName(tag.getName()).get());
            }
        }
        return t;
    }
}

package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(ReadWriteDao<Tag, Long> readWriteDao, TagDao tagDao) {
        super(readWriteDao);
        this.tagDao = tagDao;
    }

    public List<Tag> checkTagInDatabaseByName(List<Tag> tags) {
        List<Tag> newtags = null;
        if(tags != null) {
            for ( Tag item : tags ) {
                Tag result = tagDao.checkTagInDatabaseByName(item.getName());
                if( result.getName().equals("")) {
                    tagDao.persist(result);
                }
                newtags.add(result);
            }
        }
        return newtags;
    }

    @Override
    public List<Tag> backListTag(List<String> names) {
        return tagDao.backListTag(names);
    }
}

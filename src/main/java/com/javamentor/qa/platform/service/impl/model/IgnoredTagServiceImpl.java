package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.IgnoredTagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.IgnoredTagAlreadyExistsException;
import com.javamentor.qa.platform.exception.TagAlreadyExistsException;
import com.javamentor.qa.platform.exception.TagNotFoundException;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import com.javamentor.qa.platform.webapp.converters.TagConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IgnoredTagServiceImpl extends ReadWriteServiceImpl<IgnoredTag, Long> implements IgnoredTagService {
    private final IgnoredTagDao ignoredTagDao;
    private final TagService tagService;

    public IgnoredTagServiceImpl(ReadWriteDao<IgnoredTag, Long> readWriteDao,
                                 IgnoredTagDao ignoredTagDao,
                                 TagService tagService) {
        super(readWriteDao);
        this.ignoredTagDao = ignoredTagDao;
        this.tagService = tagService;
    }

    @Override
    public boolean existsByTagIdAndUserId(Long tagId, Long userId) {
        return ignoredTagDao.existsByTagIdAndUserId(tagId, userId);
    }

    @Override
    @Transactional
    public TagDto addIgnoredTag(Long tagId, User user) throws TagNotFoundException, TagAlreadyExistsException {
        Optional<Tag> tag = tagService.getById(tagId);

        if (tag.isEmpty()) {
            throw new TagNotFoundException();
        }

        if (existsByTagIdAndUserId(tagId, user.getId())) {
            throw new IgnoredTagAlreadyExistsException();
        }

        persist(new IgnoredTag(tag.get(), user));

        return TagConverter.INSTANCE.tagToTagDto(tag.get());
    }
}

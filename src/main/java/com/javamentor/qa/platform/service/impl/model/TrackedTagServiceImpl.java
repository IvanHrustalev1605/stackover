package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import com.javamentor.qa.platform.webapp.converters.TagConverter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrackedTagServiceImpl extends ReadWriteServiceImpl<TrackedTag, Long> implements TrackedTagService {

    private final TrackedTagDao trackedTagDao;
    private final TagService tagService;

    public TrackedTagServiceImpl(ReadWriteDao<TrackedTag, Long> readWriteDao,
                                 TrackedTagDao trackedTagDao,
                                 TagService tagService) {
        super(readWriteDao);
        this.trackedTagDao = trackedTagDao;
        this.tagService = tagService;
    }

    @Override
    public Optional<TagDto> saveTrackedTagByTagAndUser(Long tagId, User user) {

        Tag tag;

        if(!tagService.existsById(tagId) || trackedTagDao.existTrackedTadByUser(tagId, user.getId())) {
            return Optional.empty();
        }

        tag = tagService.getById(tagId).get();
        trackedTagDao.persist(new TrackedTag(tag, user));
        return Optional.ofNullable(TagConverter.INSTANCE.tagToTagDto(tag));
    }


}

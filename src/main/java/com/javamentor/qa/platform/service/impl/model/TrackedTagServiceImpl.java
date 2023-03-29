package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.exception.TagNotFoundException;
import com.javamentor.qa.platform.exception.TrackedTagAlreadyExistException;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class TrackedTagServiceImpl extends ReadWriteServiceImpl<TrackedTag, Long> implements TrackedTagService {

    private final TrackedTagDao trackedTagDao;
    private final TagService tagService;

    public TrackedTagServiceImpl(ReadWriteDao<TrackedTag, Long> readWriteDao, TrackedTagDao trackedTagDao, TagService tagService) {
        super(readWriteDao);
        this.trackedTagDao = trackedTagDao;
        this.tagService = tagService;
    }

    @Override
    public List<TrackedTagDto> getListUserTrackedDto(User user) {
        return trackedTagDao.getListUserTrackedDto(user);

    }
    @Transactional
    @Override
    public void addTagToTrackedTag(Long tagId,User user) {
        Optional<Tag> OptionalTag = tagService.getById(tagId);
        if (OptionalTag.isEmpty()) {
            throw new TagNotFoundException("Тэг с таким Id не найден");
        }
        if(!trackedTagDao.getTrackedTagByTagId(tagId).isEmpty()){
            throw  new TrackedTagAlreadyExistException("Тег уже был добавлен в отслеживаемые ранее.");
        }
        TrackedTag newTrackedTag =  new TrackedTag();
        newTrackedTag.setTrackedTag(OptionalTag.get());
        newTrackedTag.setUser(user);
        trackedTagDao.persist(newTrackedTag);
    }

    @Override
    public Optional<Long> getTrackedTagIdByTagId(Long TagId) {
        return trackedTagDao.getByTagId(TagId);
    }


}
package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TrackedTagServiceImpl extends ReadWriteServiceImpl<TrackedTag, Long> implements TrackedTagService {

    private final TrackedTagDao trackedTagDao;

    public TrackedTagServiceImpl(ReadWriteDao<TrackedTag, Long> readWriteDao, TrackedTagDao trackedTagDao) {
        super(readWriteDao);
        this.trackedTagDao = trackedTagDao;
    }

    @Override
    public boolean existTrackedTadByUser(Long tagId, Long userId) {
        return trackedTagDao.existTrackedTadByUser(tagId, userId);
    }

    @Override
    public void saveTrackedTag(TrackedTag trackedTag) {
        trackedTagDao.persist(trackedTag);
    }

}

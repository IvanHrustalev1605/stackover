package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TrackedTagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.TrackedTagDto;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrackedTagServiceImpl extends ReadWriteServiceImpl<TrackedTag, Long> implements TrackedTagService {

    private final TrackedTagDao trackedTagDao;

    public TrackedTagServiceImpl(ReadWriteDao<TrackedTag, Long> readWriteDao, TrackedTagDao trackedTagDao) {
        super(readWriteDao);
        this.trackedTagDao = trackedTagDao;
    }

    @Override
    public List<TrackedTagDto> getListUserTrackedDto(User user) {
        return trackedTagDao.getListUserTrackedDto(user);

    }
}
package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TrackedTagServiceImpl extends ReadWriteServiceImpl<TrackedTag, Long> implements TrackedTagService {

    public TrackedTagServiceImpl(ReadWriteDao<TrackedTag, Long> readWriteDao) {
        super(readWriteDao);
    }
}
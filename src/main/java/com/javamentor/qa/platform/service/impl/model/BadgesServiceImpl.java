package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.Badge;
import com.javamentor.qa.platform.service.abstracts.model.BadgesService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BadgesServiceImpl extends ReadWriteServiceImpl<Badge, Long> implements BadgesService {

    public BadgesServiceImpl(ReadWriteDao<Badge, Long> readWriteDao) {
        super(readWriteDao);
    }
}

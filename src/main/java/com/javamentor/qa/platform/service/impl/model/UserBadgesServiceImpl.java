package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.UserBadges;
import com.javamentor.qa.platform.service.abstracts.model.UserBadgesService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserBadgesServiceImpl extends ReadWriteServiceImpl<UserBadges, Long> implements UserBadgesService {

    public UserBadgesServiceImpl(ReadWriteDao<UserBadges, Long> readWriteDao) {
        super(readWriteDao);
    }
}

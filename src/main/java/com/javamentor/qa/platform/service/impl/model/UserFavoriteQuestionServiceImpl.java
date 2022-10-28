package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.user.UserFavoriteQuestion;
import com.javamentor.qa.platform.service.abstracts.model.UserFavoriteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserFavoriteQuestionServiceImpl extends ReadWriteServiceImpl<UserFavoriteQuestion, Long> implements UserFavoriteQuestionService {

    public UserFavoriteQuestionServiceImpl(ReadWriteDao<UserFavoriteQuestion, Long> readWriteDao) {
        super(readWriteDao);
    }
}

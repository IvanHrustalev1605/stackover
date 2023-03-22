package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.dao.impl.model.VoteQuestionDaoImpl;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.VoteQuestionService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteQuestionServiceImpl extends ReadWriteServiceImpl<VoteQuestion, Long> implements VoteQuestionService {
    private  final VoteQuestionDaoImpl voteQuestionDao;
    public VoteQuestionServiceImpl(ReadWriteDao<VoteQuestion, Long> readWriteDao, VoteQuestionDaoImpl voteQuestionDao) {
            super(readWriteDao);
            this.voteQuestionDao = voteQuestionDao;
        }

        @Transactional
        @Override
        public Long voteUp(Long questionId, User user) {
            return  voteQuestionDao.VoteUp(questionId, user);
        }
    }

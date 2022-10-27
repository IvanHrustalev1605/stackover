package com.javamentor.qa.platform.service.impl.dto.model;

import com.javamentor.qa.platform.dao.abstracts.model.VoteAnswerDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteAnswerServiceImpl extends ReadWriteServiceImpl<VoteAnswer, Long> implements VoteAnswerService {

    public final VoteAnswerDao voteAnswerDao;

    @Autowired
    public VoteAnswerServiceImpl(VoteAnswerDao voteAnswerDao) {
        super(voteAnswerDao);
        this.voteAnswerDao = voteAnswerDao;
    }


    public VoteAnswerServiceImpl(ReadWriteDao<VoteAnswer, Long> readWriteDao, VoteAnswerDao voteAnswerDao) {
        super(readWriteDao);
        this.voteAnswerDao = voteAnswerDao;
    }

    @Transactional
    @Override
    public Optional<VoteAnswer> getByAnswerIdAndUserId(Long answerId, Long userId) {
        return voteAnswerDao.getByAnswerIdAndUserId(answerId, userId);
    }
}

package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;

import java.util.Optional;

public interface VoteAnswerDao extends ReadWriteDao<VoteAnswer, Long> {



    Optional<VoteAnswer> getById(Long id);

    Optional<VoteAnswer> getVoteAnswerByAnswerIdAndUserId(Long answerId, Long userId);

    Long countVotes(Long answerId);

    @Override
    Optional<VoteAnswer> getById(Long id);
    Optional<VoteAnswer> getVotedAnswerByAnswerIdAndUserId(Long answerId, Long userId);
    Long countVotes(Long answerId);

}


package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;
public interface VoteQuestionDao extends ReadWriteDao<VoteQuestion, Long> {
    Optional<VoteQuestion> getVoteByQuestionAndByUser(Long questionId, Long userId);
    Long sumVoteForQuestion(Long questionId);
    Optional<VoteQuestion> getByQuestionIdAndUserId(Long questionId, Long userId);
    Long countOfVotes(Long questionId, User user);
}

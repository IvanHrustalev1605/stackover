package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl extends ReadWriteDaoImpl<UserDto, Long> implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getById(Long id) {
    return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                "SELECT User.id, User.email, User.fullName as fullName, User.imageLink as linkImage, User.city, Reputation.count as reputation, User.persistDateTime as registrationDate," +
                        "count(VoteQuestion.vote) + count(VoteAnswer.voteType) as votes " +
                        "FROM User " +
                        "LEFT JOIN VoteAnswer ON User.id = VoteAnswer.user.id " +
                        "LEFT JOIN VoteQuestion ON User.id = VoteQuestion.user.id " +
                        "LEFT JOIN Reputation ON User.id = Reputation.author.id AND User.id = Reputation.sender.id " +
                        "WHERE User.id=:id " +
                        "GROUP BY User.id, User.email, User.fullName, User.imageLink, User.city, Reputation.count, User.persistDateTime")
                .setParameter("id", id));
    }
}
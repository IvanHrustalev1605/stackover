package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserDto;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl extends ReadWriteDaoImpl<UserDto, Long> implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<UserDto> getById(Long id) {

        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.UserDto (
                u.id,
                u.email,
                u.fullName,
                u.imageLink,
                u.city,
                (SELECT COALESCE(SUM(r.count), 0) FROM Reputation r WHERE r.author.id = u.id),
                u.persistDateTime,
                (SELECT COUNT(DISTINCT a) + COUNT(DISTINCT q)
                FROM VoteAnswer a, VoteQuestion q
                WHERE a.user.id = u.id AND q.user.id = u.id
                ))
                FROM User u
                JOIN Reputation r ON u.id = r.author.id
                WHERE u.id = :id
                GROUP BY u.id
                """, UserDto.class).setParameter("id", id));

    }

    public List<UserDto> getAll() {

        return entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.UserDto (
                u.id,
                u.email,
                u.fullName,
                u.imageLink,
                u.city,
                (SELECT COALESCE(SUM(r.count), 0) FROM Reputation r WHERE r.author.id = u.id),
                u.persistDateTime,
                (SELECT COUNT(DISTINCT a) + COUNT(DISTINCT q)
                FROM VoteAnswer a, VoteQuestion q
                WHERE a.user.id = u.id AND q.user.id = u.id
                ))
                FROM User u
                 """).getResultList();

    }
}
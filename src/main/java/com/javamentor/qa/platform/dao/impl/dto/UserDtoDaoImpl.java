package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl extends ReadWriteDaoImpl<UserDto, Long> implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getById(Long id) {
         TypedQuery<UserDto> userDto = entityManager.createQuery(
                "SELECT NEW com.javamentor.qa.platform.models.dto.UserDto(" +
                        "u.id, " +
                        "u.email, " +
                        "u.fullName, " +
                        "u.imageLink, " +
                        "u.city, " +
                        "CAST((SELECT SUM(r.count) FROM Reputation r WHERE r.author.id = :user) AS long), " +
                        "u.persistDateTime, " +
                        "CAST((SELECT COUNT(DISTINCT v.answer) FROM VoteAnswer v WHERE v.user.id = :user) AS long)) " +
                        "FROM User u WHERE u.id = :user", UserDto.class);
        userDto.setParameter("user", id);
        return Optional.of(userDto.getSingleResult());
    }
}

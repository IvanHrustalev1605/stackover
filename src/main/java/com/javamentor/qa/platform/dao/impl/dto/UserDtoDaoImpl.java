package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl extends ReadWriteDaoImpl<UserDto, Long> implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<UserDto> getUserDtoById(long id) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.id=:id", UserDto.class)
                    .setParameter("id", id).getSingleResult());
        } catch (RuntimeException e) {
            throw new RuntimeException("User not found");
        }
    }
}
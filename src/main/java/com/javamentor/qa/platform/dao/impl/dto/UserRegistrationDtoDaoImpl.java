package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserRegistrationDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserRegistrationDtoDaoImpl extends ReadWriteDaoImpl<UserRegistrationDto, Long> implements UserRegistrationDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<UserRegistrationDto> getUserRegistrationDtoByActivationCode(String activationCode) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT  substring(User.fullName,1) as first_name, substring(User.fullName,1)  as last_name, User.email as email, User.password as password, User.activationCode as activation_code
                                                FROM User
                                                WHERE User.activationCode=:activationCode
                        """)
                .setParameter("activationCode", activationCode));
    }

}

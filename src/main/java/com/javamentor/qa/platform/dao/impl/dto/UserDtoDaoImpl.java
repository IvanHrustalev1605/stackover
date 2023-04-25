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
    public Optional<UserDto> getUserDtoByUserId(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("""
                        SELECT new com.javamentor.qa.platform.models.dto.UserDto(
                        u.id,
                        u.email,
                        u.fullName,
                        u.imageLink,
                        u.city,
                        coalesce(sum(r.count), 0),
                        u.lastUpdateDateTime,
                        coalesce(sum(case when v.voteType = 'UP' then 1 when v.voteType = 'DOWN' then -1 end), 0))
                        from User as u
                        left join Reputation as r on u.id = r.author.id
                        left join VoteAnswer as v on u.id = v.user.id
                        where u.id = :id
                        group by u.id,v.voteType,r.count
                        """, UserDto.class)
                .setParameter("id", id));
    }
}

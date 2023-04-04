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

    @Override
    public Optional<UserDto> getById(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.UserDto(
                      u.id,
                      u.email,
                      u.fullName,
                      u.imageLink,
                      u.city,
                      COALESCE(SUM(r.count), 0),
                      u.persistDateTime,
                      (SELECT COUNT(DISTINCT va) + COUNT(DISTINCT vq)
                              FROM VoteAnswer va, VoteQuestion vq
                             WHERE va.user.id = :userId
                               AND vq.user.id = :userId))
                FROM User u
                LEFT JOIN Reputation r ON u.id = r.author.id
                WHERE u.id = :userId
                GROUP BY u.id
                """, UserDto.class)
                .setParameter("userId", id));
    }

    @Override
    public Optional<List<UserDto>> getUserDtoItemsForPagination(Long itemsOnPage, Long currentPage) {
            return Optional.ofNullable(entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.UserDto(
                u.id,
                u.email,
                u.fullName,
                u.imageLink,
                u.city,
                    (SELECT COALESCE(SUM(r.count), 0)
                        FROM Reputation r
                        WHERE r.author.id = u.id),
                u.persistDateTime,
                    (SELECT COUNT(DISTINCT va) + COUNT(DISTINCT vq)
                         FROM VoteAnswer va, VoteQuestion vq
                         WHERE va.user.id = u.id
                         AND vq.user.id = u.id))
                FROM User u 
                """,UserDto.class).setFirstResult((int) ((currentPage - 1) * itemsOnPage)).setMaxResults(Math.toIntExact(itemsOnPage))
                    .getResultList());
        }

}

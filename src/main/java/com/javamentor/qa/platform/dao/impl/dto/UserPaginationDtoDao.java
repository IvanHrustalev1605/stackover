package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDtoDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;


@Repository("all users")
public class UserPaginationDtoDao implements PaginationDtoDao<UserDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int getCountOfAllItems(Map<String, Object> parameters) {
        return Math.toIntExact(entityManager.createQuery("""
                select COUNT (u)
                from User  u
                where u.isDeleted = false
                """, Long.class).getSingleResult());
    }

    @Override
    public List<UserDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        return   entityManager.createQuery("""
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
                """,UserDto.class).setFirstResult((currentPage - 1) * itemsOnPage).setMaxResults(Math.toIntExact(itemsOnPage))
                .getResultList();
    }
}

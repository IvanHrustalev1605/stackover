package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDto;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("allUsers")
public class UserDtoPaginationDtoImpl implements PaginationDto<UserDto> {

    @PersistenceContext
    private EntityManager entityManager;

    //Список всех элементов
    @Override
    public List<UserDto> getItems(Map<String, Object> param) {

        int itemsOnPage = (int) param.get("itemsOnPage");

        int currentPageNumber = (int) param.get("currentPageNumber");


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
                         """)
                .setMaxResults(itemsOnPage)
                .setFirstResult((currentPageNumber - 1) * itemsOnPage)
                .getResultList();
    }

    //Общее количество данных в бд
    @Override
    public int getTotalResultCount(Map<String, Object> param) {

        return entityManager.createQuery
                ("select count(u) from User u", Long.class).getSingleResult().intValue();
    }
}

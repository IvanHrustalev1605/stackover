package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.pagination.PaginationUserDtoDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository(value = "PaginationUserDtoDaoImpl")
public class PaginationUserDtoDaoImpl implements PaginationUserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginationUserDtoDaoImpl() {
    }

    @Override
    public int getCountOfAllItems(Map<String, Object> parameters) {
        return Math.toIntExact(entityManager.createQuery("SELECT COUNT (u) " +
                        "FROM User u", Long.class)
                .getSingleResult());
    }

    @Override
    public List<UserDto> getItems(Map<String, Object> parameters) {
        int currentPageNumber = (Integer) parameters.get("currentPage");
        int itemsOnPage = (Integer) parameters.get("itemsOnPage");
        return entityManager.createQuery("""
                        SELECT new com.javamentor.qa.platform.models.dto.UserDto(
                        u.id,
                        u.email,
                        u.fullName,
                        u.imageLink,
                        u.city,
                        (SELECT sum(r.count) FROM Reputation r WHERE r.author.id = u.id),
                        u.persistDateTime,
                        (SELECT count(a.voteType) + count(distinct q.vote) FROM VoteAnswer a, VoteQuestion q where a.user.id = u.id and q.user.id = u.id))
                        FROM User u""", UserDto.class)
                .setMaxResults(itemsOnPage)
                .setFirstResult((currentPageNumber - 1) * itemsOnPage)
                .getResultList();
    }

}

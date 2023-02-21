package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.IgnoredTagDtoDao;
import com.javamentor.qa.platform.models.dto.IgnoredTagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IgnoredTagDtoDaoImpl implements IgnoredTagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IgnoredTagDto> getAllByUserId(Long id) {
        return entityManager
                .createQuery("""
                        select new com.javamentor.qa.platform.models.dto.IgnoredTagDto(
                        it.ignoredTag.id,
                        it.ignoredTag.name)
                        from IgnoredTag it where it.user.id =: id""", IgnoredTagDto.class)
                .setParameter("id", id)
                .getResultList();
    }

}

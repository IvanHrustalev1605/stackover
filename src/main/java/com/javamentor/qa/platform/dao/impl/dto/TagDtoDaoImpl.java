package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TagDto> getById(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                SELECT new com.javamentor.qa.platform.models.dto.TagDto(
                      t.id,
                      t.name,
                      t.description)
                FROM Tag t
                WHERE t.id = :id
                """).setParameter("id", id));
    }

    //TODO
    @Override
    public Optional<List<RelatedTagDto>> getTopTags() {
        return Optional.ofNullable(entityManager.createQuery("""
                            SELECT NEW com.javamentor.qa.platform.models.dto.RelatedTagDto (
                            t.id,
                            t.name,
                            (select count(*) from t.questions t1 where t1.id in (select id from Question)))
                            FROM Tag as t
                            order by 3 desc
                """, RelatedTagDto.class)
                .setMaxResults(10)
                .getResultList());
    }

    @Override
    public Optional<List<TagDto>> getTop3TagsByUserId(Long id) {
        return Optional.of(entityManager.createQuery("""
            select new com.javamentor.qa.platform.models.dto.TagDto (topt.tid, topt.tname, topt.tdescription)
            from (select t.id as tid, t.name as tname, t.description as tdescription, SUM(r.count) as rsum
            from VoteAnswer va
            inner join User u on u.id = va.user.id
            inner join TrackedTag tract on u.id = tract.user.id
            inner join Tag t on tract.trackedTag.id = t.id
            inner join Reputation r on u.id = r.author.id
            where u.id = :id
            group by t.id, t.name, t.description, u.id, r.author.id, r.count
            order by rsum desc
            limit 3) as topt order by topt.rsum desc
            """, TagDto.class).setParameter("id", id).getResultList());

    }
}

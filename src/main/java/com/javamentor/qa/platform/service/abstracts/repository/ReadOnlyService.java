package com.javamentor.qa.platform.service.abstracts.repository;

import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReadOnlyService<E, K> {
    List<E> getAll();

    boolean existsById(K id);

    Optional<E> getById(K id);

    List<E> getAllByIds(Iterable<K> ids);

    boolean existsByAllIds(Collection<K> ids);

    Optional<Reputation> getReputation(Long answerId, Long authorId);

    Optional<E> getByAnswerIdAndUserId(Long answerId, Long userId);

}

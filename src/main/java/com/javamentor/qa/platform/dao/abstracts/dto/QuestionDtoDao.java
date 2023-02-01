package com.javamentor.qa.platform.dao.abstracts.dto;

import java.util.Optional;

public interface QuestionDtoDao {
    public Optional<Long> getById (Long questionId, Long authorizedUserId);
}

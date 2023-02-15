package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;

public interface CommentAnswerDtoService {
    Long addComment(User user, Long answerId, String text);

    CommentAnswerDto getCommentAnswerDto(Long commentAnswerId);
}

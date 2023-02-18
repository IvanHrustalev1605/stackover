package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentAnswerDtoDao;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAnswerDtoServiceImpl implements CommentAnswerDtoService {

    private final CommentAnswerDtoDao commentAnswerDtoDao;
    @Override
    public CommentAnswerDto getCommentAnswerDtoById(Long answerId) {
        return commentAnswerDtoDao.getCommentAnswerDto(answerId);
    }


}

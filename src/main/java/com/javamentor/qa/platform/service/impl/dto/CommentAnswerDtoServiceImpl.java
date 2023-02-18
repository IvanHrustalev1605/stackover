package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.model.CommentAnswerDao;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAnswerDtoServiceImpl implements CommentAnswerDtoService {

    private final CommentAnswerDao commentAnswerDao;
    private final AnswerService answerService;


    @Override
    public CommentAnswerDto addCommentToAnswerDto(CommentAnswer commentAnswer) {
        return new CommentAnswerDto(commentAnswer.getId(),
                commentAnswer.getAnswer().getId(),
                commentAnswer.getComment().getLastUpdateDateTime(),
                commentAnswer.getComment().getPersistDateTime(),
                commentAnswer.getComment().getText(),
                commentAnswer.getComment().getUser().getId(),
                commentAnswer.getComment().getUser().getImageLink(),
                100L); // TODO заглушка
    }
}

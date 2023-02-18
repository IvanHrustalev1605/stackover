package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.CommentDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionCommentDto;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentDtoServiceImpl implements CommentDtoService {
    private final CommentDtoDao commentDtoDao;
    private final QuestionService questionService;
    @Override
    public List<QuestionCommentDto> getAllCommentsOnQuestion(Long questionId) throws NotFoundException {
        if (!questionService.existsById(questionId)) {
            throw new NotFoundException(String.format("The question with id = %s does not exist", questionId));
        }

        Optional<List<QuestionCommentDto>> optionalQuestionCommentDtoList = commentDtoDao.getAllQuestionCommentDtoById(questionId);
        if (optionalQuestionCommentDtoList.isPresent()) {
            return optionalQuestionCommentDtoList.get();
        } else {
            throw new NotFoundException("There are no comments for the question");
        }
    }
}

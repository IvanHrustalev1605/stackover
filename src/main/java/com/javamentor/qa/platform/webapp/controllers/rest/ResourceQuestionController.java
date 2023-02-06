package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.QuestionDtoDaoImpl;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user/question")
public class ResourceQuestionController {

    private QuestionDtoDao questionDtoDao;

    ResourceQuestionController(QuestionDtoDaoImpl questionDtoDao) {
        this.questionDtoDao = questionDtoDao;
    }

    @ApiOperation("api возвращает QuestionDto по id вопроса")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<QuestionDto>> getQuestionDto(@PathVariable("id") long id,
                                                                @AuthenticationPrincipal User user) {
        Optional<QuestionDto> questionDto = questionDtoDao.getById(id, 1l);
        return ResponseEntity.ok(questionDto);
    }

}

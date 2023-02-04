package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.QuestionDtoDaoImpl;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<QuestionDto>> getQuestionDto(@PathVariable("id") long id,
                                                                @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(questionDtoDao.getById(id, 1l));
    }

}

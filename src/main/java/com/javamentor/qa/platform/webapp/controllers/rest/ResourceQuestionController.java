package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;
import java.util.Optional;

@RestController
public class ResourceQuestionController {

    private QuestionDtoDao questionDtoDao;

    @GetMapping("/api/user/question/{id}")
    public Optional<QuestionDto> findQuestionByIDd(@PathVariable("id") long id) {
        Optional<QuestionDto> questionDto = questionDtoDao.getById(id, 1l);
        return questionDto;
    }

}

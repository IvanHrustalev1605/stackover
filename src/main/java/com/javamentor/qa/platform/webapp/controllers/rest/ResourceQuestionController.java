package com.javamentor.qa.platform.webapp.controllers.rest;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.impl.model.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/question")
public class ResourceQuestionController {
    private final QuestionDtoService questionDtoService;
    private final UserServiceImpl userServiceImpl;
    public ResourceQuestionController(QuestionDtoService questionDtoService, UserServiceImpl userServiceImpl) {
        this.questionDtoService = questionDtoService;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/addQuestion") // api которое добавляет новый вопрос, возвращает QuestionDto
    public ResponseEntity<QuestionDto> addQuestion(@RequestBody QuestionCreateDto questionCreateDto,@AuthenticationPrincipal User user) {

        //return ResponseEntity.ok(questionDtoService.addQuestion(questionCreateDto, user)); TODO -Mock
        return ResponseEntity.ok(questionDtoService.addQuestion(questionCreateDto, userServiceImpl.getById(1L).get() ));
    }
}

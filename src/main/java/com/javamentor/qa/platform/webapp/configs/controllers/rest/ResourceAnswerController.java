//package com.javamentor.qa.platform.webapp.configs.controllers.rest;
//
//
//import com.javamentor.qa.platform.models.dto.AnswerDto;
//import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/user/question/{questionId}/answer")
//@Tag(name="Answer", description="get Answer")
//public class ResourceAnswerController {
//
//    private final AnswerService answerService;
//
//   @Autowired
//    public ResourceAnswerController(AnswerService answerService) {
//        this.answerService = answerService;
//    }
//
//    @GetMapping("/{questionId}")
//    public ResponseEntity<AnswerDto> getAllAnswer(@PathVariable("questionId") Long id,
//                                                  @PathVariable("userId") Long id1) {
//        List<AnswerDto> answerDtos = answerService.getAllAnswerDtoQuestionId(id, id1);
//
//    }
//
//
//}

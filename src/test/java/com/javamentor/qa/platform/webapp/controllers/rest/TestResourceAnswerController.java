package com.javamentor.qa.platform.webapp.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javamentor.qa.platform.models.dto.CommentAnswerDto;
import com.javamentor.qa.platform.models.dto.question.answer.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.CommentAnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import javassist.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = JmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResourceAnswerController {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerDtoService answerDtoService;
    @MockBean
    private QuestionService questionService;

    @MockBean
    private CommentAnswerDtoService commentAnswerDtoService;



    @Test
    public void getAllAnswers_AnswersFound_ShouldReturnAnswersDtoList() throws Exception {
        AnswerDto answerDto = new AnswerDto(1L, 1L, 1L, "answer text", LocalDateTime.now(),
                false, LocalDateTime.now(), 3L, 4L, "user.png", "User", 3L, VoteType.UP);
        AnswerDto answerDto1 = new AnswerDto(1L, 2L, 1L, "answer2 text", LocalDateTime.now(),
                false, LocalDateTime.now(), 5L, 3L, "user2.png", "User2", 6L, VoteType.UP);

        List<AnswerDto> answers = List.of(answerDto, answerDto1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        given(questionService.existsById(1L)).willReturn(true);
        given(answerDtoService.getAllAnswersDtoByQuestionId(1L, 1L)).willReturn(answers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(answers)));
    }

    @Test
    public void getAllAnswers_AnswersNotFound_ShouldReturn404Status() throws Exception {
        given(questionService.existsById(1L)).willReturn(true);
        given(answerDtoService.getAllAnswersDtoByQuestionId(1L, 1L)).willThrow(new NotFoundException("Answers not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getAllAnswers_QuestionIdDoesNotExist_ShouldReturn400Status() throws Exception {
        given(questionService.existsById(1L)).willReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/question/{questionId}/answer", 1L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void addComment() throws Exception {
        // given
        String dateTimeString = "2023-02-05T15:47:32.507115813";
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setEmail("user@mail.test");
        user.setFullName("User Full Name");
        user.setPersistDateTime(dateTime);
        user.setIsEnabled(true);
        user.setIsDeleted(false);
        user.setCity("Moscow");
        user.setLinkSite("http://user-site.test");
        user.setLinkGitHub(null);
        user.setLinkVk(null);
        user.setAbout("User bio");
        user.setImageLink("http://user-site.test/image.jpg");
        user.setLastUpdateDateTime(dateTime);
        user.setNickname("user-nick-name");
        user.setRole(role);

        Question question = new Question();
        question.setId(1L);
        question.setTitle("Question title");
        question.setDescription("Question description");
        question.setPersistDateTime(dateTime);
        question.setUser(user);
        question.setIsDeleted(false);

        Answer answer = new Answer();
        answer.setId(1L);
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setCommentAnswers(List.of());
        answer.setVoteAnswers(List.of());
        answer.setIsDeleted(false);
        answer.setHtmlBody("answer html body");
        answer.setIsHelpful(true);
        answer.setIsDeletedByModerator(false);
        answer.setDateAcceptTime(dateTime);
        answer.setUpdateDateTime(dateTime);

        CommentAnswerDto commentAnswerDto = new CommentAnswerDto();
        commentAnswerDto.setId(1L);
        commentAnswerDto.setAnswerId(1L);
        commentAnswerDto.setText("comment text");
        commentAnswerDto.setImageLink(user.getImageLink());
        commentAnswerDto.setReputation(10L);
        commentAnswerDto.setUserId(user.getId());
        commentAnswerDto.setPersistDate(dateTime);
        commentAnswerDto.setLastRedactionDate(dateTime);

//        given(commentAnswerDtoService.addComment(BDDMockito.any(), BDDMockito.eq(1L), BDDMockito.eq("comment text"))).willReturn(1L);
//        BDDMockito.given(commentAnswerDtoService.getCommentAnswerDto(1L)).willReturn(commentAnswerDto);

        // when
        mockMvc.perform(MockMvcRequestBuilders
                                .post("/api/user/question/{questionId}/answer/{answerId}/comment", 1, 1)
                                .contentType(MediaType.TEXT_PLAIN)
                                .content("comment text"))

               // then
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.answerId", Matchers.is(1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.lastRedactionDate", Matchers.is(dateTimeString)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.persistDate", Matchers.is(dateTimeString)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.is("comment text")))
               .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.imageLink", Matchers.is(user.getImageLink())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.reputation", Matchers.is(10)));
    }
}

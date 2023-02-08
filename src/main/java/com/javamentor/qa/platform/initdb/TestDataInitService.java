package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.VoteQuestion;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataInitService {

    private final RoleService roleService;
    private final UserService userService;
    private final TagService tagService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final VoteQuestionService voteQuestionService;
    private final VoteAnswerService voteAnswerService;

    @Autowired
    public TestDataInitService(RoleService roleService, UserService userService,
                               TagService tagService, QuestionService questionService,
                               AnswerService answerService,
                               VoteQuestionService voteQuestionService,
                               VoteAnswerService voteAnswerService) {
        this.roleService = roleService;
        this.userService = userService;
        this.tagService = tagService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.voteQuestionService = voteQuestionService;
        this.voteAnswerService = voteAnswerService;
    }

    @Transactional
    public void createEntity() {
        createRoles();
        createUsers();
        createTags();
        createQuestions();
        createAnswers();
        createVoteQuestions();
        createVoteAnswer();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    List<User> users = new ArrayList<>();

    private void createUsers() {
        User user = new User();
        user.setEmail("user@mail.ru");
        user.setPassword(passwordEncoder().encode("user"));
        user.setFullName("User User");
        user.setNickname("U$er");
        user.setRole(roleService.getById(1L).orElseThrow(IllegalArgumentException::new));

        User admin = new User();
        admin.setEmail("admin@mail.ru");
        admin.setPassword(passwordEncoder().encode("admin"));
        admin.setFullName("Admin Admin");
        admin.setNickname("Admin777");
        admin.setRole(roleService.getById(2L).orElseThrow(IllegalArgumentException::new));

        users = List.of(user, admin);
        userService.persistAll(users);
    }

    List<Role> roles = new ArrayList<>();

    private void createRoles() {
        roles = List.of(new Role("ROLE_USER"),
                new Role("ROLE_ADMIN"));
        roleService.persistAll(roles);
    }

    List<Tag> tags = new ArrayList<>();

    private void createTags() {
        Tag tag1 = new Tag();
        tag1.setName("Tag1");
        tag1.setDescription("TagDesc1");
        tag1.setPersistDateTime(LocalDateTime.now());

        Tag tag2 = new Tag();
        tag2.setName("Tag2");
        tag2.setDescription("TagDesc2");
        tag2.setPersistDateTime(LocalDateTime.now());

        Tag tag3 = new Tag();
        tag3.setName("Tag3");
        tag3.setDescription("TagDesc3");
        tag3.setPersistDateTime(LocalDateTime.now());

        tags = List.of(tag1, tag2, tag3);
        tagService.persistAll(tags);
    }

    List<Question> questions = new ArrayList<>();

    private void createQuestions() {
        Question question1 = new Question();
        question1.setTitle("QuestionTitle1");
        question1.setDescription("QuestionDesc1");
        question1.setPersistDateTime(LocalDateTime.now());
        question1.setIsDeleted(false);
        question1.setUser(userService.getById(1L).orElseThrow(IllegalArgumentException::new));
        question1.setTags(List.of(tagService.getById(1L).orElseThrow(IllegalArgumentException::new)));
        System.out.println(question1.getTags());
        Question question2 = new Question();
        question2.setTitle("QuestionTitle2");
        question2.setDescription("QuestionDesc2");
        question2.setPersistDateTime(LocalDateTime.now());
        question2.setIsDeleted(false);
        question2.setUser(userService.getById(2L).orElseThrow(IllegalArgumentException::new));
        question2.setTags(List.of(tagService.getById(2L).orElseThrow(IllegalArgumentException::new)));
        System.out.println(question2.getTags());
        questions = List.of(question1, question2);

        questionService.persistAll(questions);
    }

    List<Answer> answers = new ArrayList<>();

    private void createAnswers() {
        Answer answer1 = new Answer();
        answer1.setDateAcceptTime(LocalDateTime.now());
        answer1.setHtmlBody("HtmlBodyAnswer1");
        answer1.setIsDeleted(false);
        answer1.setIsDeletedByModerator(false);
        answer1.setIsHelpful(true);
        answer1.setPersistDateTime(LocalDateTime.now());
        answer1.setUpdateDateTime(LocalDateTime.now());
        answer1.setQuestion(questionService.getById(2L).orElseThrow(IllegalArgumentException::new));
        answer1.setUser(userService.getById(1L).orElseThrow(IllegalArgumentException::new));

        Answer answer2 = new Answer();
        answer2.setDateAcceptTime(LocalDateTime.now());
        answer2.setHtmlBody("HtmlBodyAnswer2");
        answer2.setIsDeleted(false);
        answer2.setIsDeletedByModerator(false);
        answer2.setIsHelpful(false);
        answer2.setPersistDateTime(LocalDateTime.now());
        answer2.setUpdateDateTime(LocalDateTime.now());
        answer2.setQuestion(questionService.getById(1L).orElseThrow(IllegalArgumentException::new));
        answer2.setUser(userService.getById(2L).orElseThrow(IllegalArgumentException::new));

        answers = List.of(answer1, answer2);

        answerService.persistAll(answers);
    }

    List<VoteQuestion> voteQuestions = new ArrayList<>();

    private void createVoteQuestions() {
        VoteQuestion voteQuestion1 = new VoteQuestion();
        voteQuestion1.setUser(userService.getById(1L).orElseThrow(IllegalArgumentException::new));
        voteQuestion1.setQuestion(questionService.getById(2L).orElseThrow(IllegalArgumentException::new));
        voteQuestion1.setLocalDateTime(LocalDateTime.now());
        voteQuestion1.setVote(VoteType.UP);

        VoteQuestion voteQuestion2 = new VoteQuestion();
        voteQuestion2.setUser(userService.getById(2L).orElseThrow(IllegalArgumentException::new));
        voteQuestion2.setQuestion(questionService.getById(1L).orElseThrow(IllegalArgumentException::new));
        voteQuestion2.setLocalDateTime(LocalDateTime.now());
        voteQuestion2.setVote(VoteType.DOWN);

        voteQuestions = List.of(voteQuestion1, voteQuestion2);

        voteQuestionService.persistAll(voteQuestion1, voteQuestion2);
    }

    List<VoteAnswer> voteAnswers = new ArrayList<>();

    private void createVoteAnswer() {
        VoteAnswer voteAnswer1 = new VoteAnswer();
        voteAnswer1.setUser(userService.getById(1L).orElseThrow(IllegalArgumentException::new));
        voteAnswer1.setAnswer(answerService.getById(2L).orElseThrow(IllegalArgumentException::new));
        voteAnswer1.setPersistDateTime(LocalDateTime.now());
        voteAnswer1.setVoteType(VoteType.UP);

        VoteAnswer voteAnswer2 = new VoteAnswer();
        voteAnswer2.setUser(userService.getById(2L).orElseThrow(IllegalArgumentException::new));
        voteAnswer2.setAnswer(answerService.getById(1L).orElseThrow(IllegalArgumentException::new));
        voteAnswer2.setPersistDateTime(LocalDateTime.now());
        voteAnswer2.setVoteType(VoteType.DOWN);

        voteAnswers = List.of(voteAnswer1, voteAnswer2);

        voteAnswerService.persistAll(voteAnswer1, voteAnswer2);
    }

    List<Reputation> reputations = new ArrayList<>();

    private void createReputations() {

    }
}

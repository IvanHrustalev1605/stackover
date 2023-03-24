package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestDataInitService {

    private final EntityManager entityManager;
    private final UserService userService;


    public TestDataInitService(EntityManager entityManager, UserService userService) {
        this.entityManager = entityManager;
        this.userService = userService;
    }

    @Transactional
    public void createEntity() {
        createUsers();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    List<User> users = new ArrayList<>();

    private void createUsers() {
        User user = new User();
        user.setEmail("user@mail.com");
        user.setCity("Moscow");
        user.setFullName("Ivan Ivanov");
        user.setPassword("j5dfhyd34*2f");
        user.setLinkSite("google.com");
        user.setLinkGitHub("https://github.com/user");
        user.setAbout("My name is Ivan. I'm from Moscow.");
        user.setLinkVk("https://vk.com/id001");
        user.setNickname("VanyaMoscow");
        user.setRole(new Role("ROLE_USER"));

        User admin = new User();
        admin.setEmail("admin@mail.com");
        admin.setCity("Moscow2");
        admin.setFullName("Nikolay Ivanov");
        admin.setPassword("j5d23d34*2f");
        admin.setLinkSite("www.google.com");
        admin.setLinkGitHub("https://github.com/admin");
        admin.setAbout("My name is Nikolay. I'm from Moscow.");
        admin.setLinkVk("https://vk.com/id002");
        admin.setNickname("KolyaMoscow");
        admin.setRole(new Role("ROLE_ADMIN"));

       if (userService.getByEmail(user.getEmail()).isEmpty()) {
            entityManager.persist(user);
       }
       if (userService.getByEmail(admin.getEmail()).isEmpty()) {
            entityManager.persist(admin);
        }

        //Обновление паролей для пользователей с учетом Bcrypt. Для user'a новый пароль "user", для admin'a "admin".
        Optional<User> user1 = userService.getByEmail(user.getEmail());
        Optional<User> user2 = userService.getByEmail(admin.getEmail());
        if (user1.isPresent()) {
            if (user1.get().getPassword().equals("j5dfhyd34*2f")) {
                user1.get().setPassword("$2a$12$9MUhEo3u5XYd5DRFDHYv4ed0frbEtc5jBIj.CnXlEXTOnL.ynxQiO");
                userService.update(user1.get());
            }
        }
        if (user2.isPresent()) {
            if (user2.get().getPassword().equals("j5d23d34*2f")) {
                user2.get().setPassword("$2a$12$kd.LyaLKXAYBwAU7Id6TUukZjrNzPly732jJbaJuTuDLaPtNhRnIO");
                userService.update(user2.get());
            }
        }
       }
        entityManager.flush();

    List<Tag> tags = new ArrayList<>();

    private void createTags() {

    }

    List<Question> questions = new ArrayList<>();

    private void createQuestions() {

    }

    List<Answer> answers = new ArrayList<>();

    private void createAnswers() {

    }

    List<Reputation> reputations = new ArrayList<>();

    private void createReputations() {

    }
}

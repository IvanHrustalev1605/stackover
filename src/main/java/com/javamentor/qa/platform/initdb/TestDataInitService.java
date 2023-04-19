package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.dao.abstracts.model.RoleDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDataInitService {

    private final UserDao userDao;
    private final RoleDao roleDao;


    @Transactional
    public void createEntity() {
        createRoles();
        createUsers();
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void createRoles() {
        List<Role> roles = List.of(new Role("ADMIN"), new Role("USER"));

        roleDao.persistAll(roles);
    }
    List<User> users = new ArrayList<>();

    private void createUsers() {
        var alex = new User(null, "alex@mail.com", passwordEncoder().encode("123"),
                "Alex Duncan", LocalDateTime.now(), true, false, "NYC",
                null, null, null, "some", null,
                null, null, roleDao.getByName("ADMIN").orElseThrow());

        var bob = new User(null, "bob@mail.com", passwordEncoder().encode("321"),
                "Bob Jones ", LocalDateTime.now(), true, false, "Los Angeles",
                null, null, null, "some", null,
                null, null, roleDao.getByName("USER").orElseThrow());

        users.add(alex);
        users.add(bob);

        userDao.persistAll(users);
    }


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

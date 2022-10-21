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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDataInitService {

    private final RoleDao roleDao;

    private final UserDao userDao;

    @Transactional
    public void createEntity() {
        createRoles();
        createUsers();
    }

    private void createRoles() {
        List<Role> roles = List.of(new Role("admin"), new Role("user"));
        roleDao.persistAll(roles);
    }

    private void createUsers() {
        List<User> users = List.of(
                new User("user1@gmail.com", "user1", roleDao.getByName("user").orElseThrow()),
                new User("admin1@gmail.com", "admin1", roleDao.getByName("admin").orElseThrow()),
                new User("admin2@gmail.com", "admin2", roleDao.getByName("admin").orElseThrow()),
                new User("user2@gmail.com", "user2", roleDao.getByName("user").orElseThrow()));
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

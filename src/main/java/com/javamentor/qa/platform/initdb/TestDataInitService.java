package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataInitService {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public TestDataInitService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public void createEntity() {

        createRoles();
        createUsers();

    }

    private void createRoles(){

        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");

        roleService.persist(user);
        roleService.persist(admin);
    }

    private void createUsers() {

        User user = new User("user@mail.ru", passwordEncoder().encode("user"),
                "user",roleService.getById(1L).orElseThrow());

        User admin = new User("admin@mail.ru", passwordEncoder().encode("admin"),
                "admin",roleService.getById(2L).orElseThrow());

        userService.persist(user);
        userService.persist(admin);

    }

    List<User> users = new ArrayList<>();

    List<Tag> tags = new ArrayList<>();

    private void createTags() {}

    List<Question> questions = new ArrayList<>();

    private void createQuestions() {}

    List<Answer> answers = new ArrayList<>();

    private void createAnswers() {}

    List<Reputation> reputations = new ArrayList<>();

    private void createReputations() {}
}

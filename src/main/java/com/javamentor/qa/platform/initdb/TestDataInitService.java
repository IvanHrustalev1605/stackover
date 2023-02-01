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

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public TestDataInitService(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Transactional
    public void createEntity() {
        createRoles();
        createUsers();
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

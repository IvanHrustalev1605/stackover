package com.javamentor.qa.platform.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.util.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResourceAdminControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Test
    @DataSet("resource_admin_controller.yml")
    void activateUserTest() throws Exception {
        User user = userService.getById(1L).get();
        this.mockMvc.perform(post("/api/admin/{userId}/activation", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getToken(user)))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThat(user.isEnabled()).isEqualTo(true);
    }
}

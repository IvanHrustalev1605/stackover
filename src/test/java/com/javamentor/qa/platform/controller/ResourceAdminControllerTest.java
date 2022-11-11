package com.javamentor.qa.platform.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.util.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void checkUserLockTest() throws Exception {
        User user = userService.getById(1L).get();
        this.mockMvc.perform(get("/api/admin/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getToken(user)))
                .andDo(print())
                .andExpect(status().isOk());
        User userMock = Mockito.mock(User.class);
        userMock.checkUserLock();
        Mockito.verify(userMock, Mockito.times(1))
                .checkUserLock();
        Assertions.assertThat(userMock.checkUserLock()).isNotNull();
    }

    @Test
    @DataSet("resource_admin_controller.yml")
    void lockUserTest() throws Exception {
        User user = userService.getById(1L).get();
        this.mockMvc.perform(post("/api/admin/user/lock/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getToken(user)))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThat(user.isEnabled()).isEqualTo(false);
        User userMock = Mockito.mock(User.class);
        userMock.lockUser();
        Mockito.verify(userMock, Mockito.times(1))
                .lockUser();
    }

    @Test
    @DataSet("resource_admin_controller.yml")
    void unLockUserTest() throws Exception {
        User user = userService.getById(2L).get();
        this.mockMvc.perform(post("/api/admin/user/unlock/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getToken(user)))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThat(user.isEnabled()).isEqualTo(true);
        User userMock = Mockito.mock(User.class);
        userMock.unLockUser();
        Mockito.verify(userMock, Mockito.times(1))
                .unLockUser();
    }
}

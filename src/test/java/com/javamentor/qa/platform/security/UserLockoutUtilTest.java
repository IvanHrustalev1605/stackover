package com.javamentor.qa.platform.security;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.util.UserLockoutUtil;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.util.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class UserLockoutUtilTest extends BaseTest {

    @MockBean
    private UserLockoutUtil userLockoutUtil;
    @Autowired
    private UserService userService;
    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Test
    @DataSet("user_lock_util.yml")
    void checkUserLockTest() {
        User user = userService.getById(1L).get();
        userLockoutUtil.checkUserLock(user);
        Mockito.verify(userLockoutUtil, Mockito.times(1)).checkUserLock(userArgumentCaptor.capture());
        Assertions.assertThat(userArgumentCaptor.getValue()).isNotNull();
    }

    @Test
    @DataSet("user_lock_util.yml")
    void lockUserTest() {
        User user = userService.getById(1L).get();
        userLockoutUtil.lockUser(user);
        Mockito.verify(userLockoutUtil, Mockito.times(1))
                .lockUser(userArgumentCaptor.capture());
        Assertions.assertThat(userArgumentCaptor.getValue().getIsEnabled()).isEqualTo(false);
        Mockito.doReturn(true).when(userLockoutUtil).lockUser(user);
    }

    @Test
    @DataSet("user_lock_util.yml")
    void unLockUserTest() {
        User user = userService.getById(2L).get();
        userLockoutUtil.unLockUser(user);
        Mockito.verify(userLockoutUtil, Mockito.times(1))
                .unLockUser(userArgumentCaptor.capture());
        Assertions.assertThat(userArgumentCaptor.getValue().getIsEnabled()).isEqualTo(true);
        Mockito.doReturn(true).when(userLockoutUtil).unLockUser(user);
    }
}
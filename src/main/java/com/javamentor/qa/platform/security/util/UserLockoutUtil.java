package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLockoutUtil {

    @Autowired
    private final UserService userService;

    public UserLockoutUtil(UserService userService) {
        this.userService = userService;
    }

    public boolean checkUserLock(User user) {
        return user.getIsEnabled();
    }

    public boolean lockUser(User user) {
        if (!checkUserLock(user)) {
            return false;
        }
        user.setIsEnabled(false);
        userService.update(user);
        return true;
    }

    public boolean unLockUser(User user) {
        if (checkUserLock(user)) {
            return false;
        }
        user.setIsEnabled(true);
        userService.update(user);
        return true;
    }
}
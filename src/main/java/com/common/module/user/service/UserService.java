package com.common.module.user.service;

import com.common.auth.dto.SignUpRequest;
import com.common.auth.service.AuthUser;
import com.common.base.service.BaseService;
import com.common.module.user.dto.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface UserService extends BaseService<User> {

    User getByEmail(String email);
    int saveUser(SignUpRequest signUpRequest);

}

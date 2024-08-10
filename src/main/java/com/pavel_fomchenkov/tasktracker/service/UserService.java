package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User save(User user);

    User create(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

    User getCurrentUser();

    void getAdmin();
}

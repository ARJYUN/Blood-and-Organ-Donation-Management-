package com.bodms.controller;

import com.bodms.database.UserDAO;
import com.bodms.model.User;
import com.bodms.utils.PasswordUtils;

import java.util.Optional;

public class AuthController {
    private final UserDAO userDAO = new UserDAO();

    public Optional<User> login(String email, String password) {
        return userDAO.findByEmail(email)
                .filter(u -> PasswordUtils.verify(password, u.getPassword()));
    }

    public User register(String name, String email, String password, String role) throws Exception {
        // role: DONOR, RECEIVER, CHARITY
        if (!role.equals("DONOR") && !role.equals("RECEIVER") && !role.equals("CHARITY")) {
            throw new IllegalArgumentException("Invalid role");
        }
        return userDAO.create(name, email, password, role);
    }
}

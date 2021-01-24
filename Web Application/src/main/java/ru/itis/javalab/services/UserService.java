package ru.itis.javalab.services;

import ru.itis.javalab.models.User;

public interface UserService {
    User getByAuth(String auth);
    void setUser(User user);
}

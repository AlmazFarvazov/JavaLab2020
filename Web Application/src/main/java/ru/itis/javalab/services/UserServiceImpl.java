package ru.itis.javalab.services;

import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Almaz Farvazov
 * Date: 26.10.2020
 * Time: 15:15
 * Group: 11-903
 */

public class UserServiceImpl implements UserService {

    private UsersRepository repository;

    public UserServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getByAuth(String auth) {
        return repository.findByAuth(auth).orElse(null);
    }

    @Override
    public void setUser(User user) {
        repository.save(user);
    }
}

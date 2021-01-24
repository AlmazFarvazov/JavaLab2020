package ru.itis.javalab.models;

import lombok.*;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Almaz Farvazov
 * Date: 26.10.2020
 * Time: 14:09
 * Group: 11-903
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class User {
    private int id;
    private String login;
    private String password;
    private UUID auth;
}

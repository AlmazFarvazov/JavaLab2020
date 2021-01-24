package ru.itis.javalab.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entity {
    private long id;
    private String firstName;
    private String lastName;
    private int age;
}

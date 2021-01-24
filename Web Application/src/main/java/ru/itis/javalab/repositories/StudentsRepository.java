package ru.itis.javalab.repositories;

import ru.itis.javalab.models.Student;

import java.util.List;

public interface StudentsRepository extends CrudRepository<Student> {
    List<Student> findAllByAge(int age);
}

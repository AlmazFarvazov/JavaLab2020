package ru.itis.javalab.services;

import ru.itis.javalab.models.Student;
import ru.itis.javalab.repositories.StudentsRepository;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private StudentsRepository studentsRepository;

    public StudentServiceImpl(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentsRepository.findAll();
    }

    @Override
    public List<Student> getAllStudentsByAge(int age) {
        return studentsRepository.findAllByAge(age);
    }
}

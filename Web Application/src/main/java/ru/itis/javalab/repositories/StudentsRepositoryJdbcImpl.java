package ru.itis.javalab.repositories;

import ru.itis.javalab.models.Student;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class StudentsRepositoryJdbcImpl implements StudentsRepository {

    //language=SQL
    private static final String SQL_DELETE = "DELETE FROM student WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "select * from student";

    //language=SQL
    private static final String SQL_FIND_ALL_BY_AGE = "select * from student where age = ?";

    //language=SQL
    private static final String SQL_INSERT_STUDENT = "INSERT INTO student (first_name, last_name, age, group_number)" +
            " VALUES (?, ?, ?, ?)";

    //language=SQL
    private static final String  SQL_UPDATE = "UPDATE student SET first_name = ?, last_name = ?, age = ?," +
            " group_number = ? WHERE id = ?";

    private final SimpleJdbcTemplate template;

    private final RowMapper<Student> studentRowMapper = row -> Student.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .groupNumber(row.getInt("group_number"))
            .build();

    public StudentsRepositoryJdbcImpl(DataSource dataSource) {
        template = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public void save(Student entity) {
        template.update(SQL_INSERT_STUDENT, entity.getFirstName(), entity.getLastName(), entity.getAge(), entity.getGroupNumber());
    }

    @Override
    public void update(Student entity) {
        template.update(SQL_UPDATE, entity.getId());
    }

    @Override
    public void delete(Student entity) {
        template.update(SQL_DELETE, entity.getId());
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        return template.query(SQL_FIND_ALL, studentRowMapper);
    }

    @Override
    public List<Student> findAllByAge(int age) {
        return template.query(SQL_FIND_ALL_BY_AGE, studentRowMapper, age);
    }
}

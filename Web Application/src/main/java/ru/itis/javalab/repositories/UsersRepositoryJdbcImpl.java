package ru.itis.javalab.repositories;

import ru.itis.javalab.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private final SimpleJdbcTemplate template;

    //language=SQL
    private static final String SQL_DELETE = "DELETE FROM users WHERE id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM users";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "SELECT * FROM users WHERE id = ?";

    //language=SQL
    private static final String SQL_INSERT_USER = "INSERT INTO users (login, password, auth) VALUES (?, ?, ?)";

    //language=SQL
    private static final String SQL_SELECT_BY_AUTH = "SELECT * FROM users u WHERE u.auth = ?";

    //language=SQL
    private static final String SQL_UPDATE = "UPDATE users SET login = ?, password = ?, auth = ? WHERE id = ?";

    private final RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getInt("id"))
            .login(row.getString("login"))
            .password(row.getString("password"))
            .build();

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        template = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByAuth(String auth) {
        return template.query(SQL_SELECT_BY_AUTH, userRowMapper, auth).stream().findAny();
    }

    @Override
    public void save(User entity) {
        template.update(SQL_INSERT_USER, entity.getLogin(), entity.getPassword(), entity.getAuth());
    }

    @Override
    public void update(User entity) {
        template.update(SQL_UPDATE, entity.getId());
    }

    @Override
    public void delete(User entity) {
        template.update(SQL_DELETE, entity.getId());
    }

    @Override
    public Optional<User> findById(Long id) {
        return template.query(SQL_FIND_BY_ID, userRowMapper, id).stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return template.query(SQL_FIND_ALL,userRowMapper);
    }
}

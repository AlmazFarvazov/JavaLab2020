package ru.itis.javalab;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EntityManager {
    private DataSource dataSource;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) {
        // сгенерировать CREATE TABLE на основе класса
        // create table account ( id integer, firstName varchar(255), ...))
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(tableName).append(" (");
        for (Field field : fields) {
            sql.append(field.getName());
            sql.append(" ");
            switch (field.getType().getSimpleName()) {
                case "int":
                    sql.append("integer");
                    break;
                case "long":
                    sql.append("bigint");
                    break;
                case "double":
                    sql.append("double");
                    break;
                case "float":
                    sql.append("float");
                    break;
                default:
                    sql.append("varchar(255)");
            }
            sql.append(", ");
        }
        sql.delete(sql.length() - 2, sql.length());
        sql.append(");");
        System.out.println(sql);
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql.toString());
        ){
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new IllegalStateException(throwables);
        }
    }

    public void save(String tableName, Object entity) {
        Class<?> classOfEntity = entity.getClass();
        // сканируем его поля
        // сканируем значения этих полей
        // генерируем insert into
        Field[] fields = classOfEntity.getDeclaredFields();
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (");
        for (int i = 0; i < fields.length - 1; i++) {
            sql.append(fields[i].getName());
            sql.append(", ");
        }
        sql.append(fields[fields.length - 1].getName());
        sql.append(") VALUES(");
        for (int i = 0; i < fields.length - 1; i++) {
            sql.append("?, ");
        }
        sql.append("?);");
        System.out.println(sql);
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql.toString());
        ){
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                statement.setObject(i + 1, field.get(entity));
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException throwables) {
            throw new IllegalStateException(throwables);
        }
    }

    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        // сгенеририровать select
        Field[] fields = resultType.getDeclaredFields();
        T result = null;
        try {
            result = resultType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(tableName).append(" WHERE id = ?;");
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql.toString());
        ){
            if (Long.class.equals(idType)) {
                statement.setLong(1, (Long) idValue);
            } else if (Integer.class.equals(idType)) {
                statement.setInt(1, (Integer) idValue);
            } else if (String.class.equals(idType)) {
                statement.setString(1, (String) idValue);
            } else {
                statement.setObject(1, idValue);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(result, resultSet.getObject(field.getName()));
                }
            }
        } catch (SQLException | IllegalAccessException throwables) {
            throw new IllegalStateException(throwables);
        }
        return result;
    }
}

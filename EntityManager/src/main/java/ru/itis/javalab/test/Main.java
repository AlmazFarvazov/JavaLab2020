package ru.itis.javalab.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.javalab.EntityManager;

public class Main {
    public static void main(String[] args) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/javalab_pract");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("m4sl!na");
        hikariConfig.setMaximumPoolSize(20);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        EntityManager entityManager = new EntityManager(dataSource);

//        entityManager.createTable("entity", Entity.class);

//        Entity entity = new Entity(20, "Almaz", "Farvazov", 19);
//        entityManager.save("entity", entity);

        Entity entity1 = entityManager.findById("entity", Entity.class, Long.class, 20L);
        System.out.println(entity1);
    }
}

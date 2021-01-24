package ru.itis.javalab.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.repositories.UsersRepositoryJdbcImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.UUID;

public class SecurityService {

    private UsersRepository repository;
    private PasswordEncoder passwordEncoder;

    public SecurityService(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.repository = new UsersRepositoryJdbcImpl(dataSource);
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isSigned(HttpServletRequest req) {
        if (req.getSession().getAttribute("Auth") == null) {
            Cookie[] cookies = req.getCookies();
            String auth = "Auth";
            String uuid = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(auth)) {
                    uuid = cookie.getValue();
                }
            }
            if (repository.findByAuth(uuid).isPresent()) {
                req.getSession().setAttribute("Auth", uuid);
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }

    public void registrate(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UUID uuid = UUID.randomUUID();
        Cookie cookie = new Cookie("Auth", uuid.toString());
        cookie.setMaxAge(60 * 60 * 24 * 365);
        resp.addCookie(cookie);
        req.getSession().setAttribute("Auth", uuid.toString());
        password = passwordEncoder.encode(password);
        User user = User.builder()
                .login(login)
                .password(password)
                .auth(uuid)
                .build();
        repository.save(user);
    }

}

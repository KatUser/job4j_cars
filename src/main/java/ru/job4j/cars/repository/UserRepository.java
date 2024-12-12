package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sessionFactory;

    public User create(Session session, User user) {
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return user;
    }

    public void update(Session session, User user) {
        try {
            session.beginTransaction();
            session.createQuery("UPDATE User SET login = :fLogin WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public void delete(Session session, int userId) {
        try {
            session.beginTransaction();
            session.createQuery(" DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public List<User> findAllUsersOrderById() {
        try (Session session = sessionFactory.openSession()) {
            List<User> query = session
                    .createQuery("from User", User.class).list();
            query.sort(Comparator.comparing(User::getId));
            return query;
        }
    }

    public Optional<User> findById(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session
                    .createQuery("FROM User AS u WHERE u.id = " + userId, User.class);
            return query.uniqueResultOptional();
        }
    }

    public List<User> findByLikeLogin(String key) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session
                    .createQuery("FROM User AS u WHERE u.login LIKE :fKey", User.class)
                    .setParameter("fKey", String.format("%%%s%%", key));
            return query.list();
        }
    }

    public Optional<User> findByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session
                    .createQuery("FROM User AS u WHERE u.login = :fLogin", User.class)
                    .setParameter("fLogin", login);
            return query.uniqueResultOptional();
        }
    }

    public void deleteAllUsers(Session session) {
        try {
            session.beginTransaction();
            session.createQuery("DELETE from User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }
}
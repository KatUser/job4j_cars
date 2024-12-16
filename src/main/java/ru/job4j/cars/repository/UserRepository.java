package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ru.job4j.cars.model.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sessionFactory;

    public User create(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return user;
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery("UPDATE User SET login = :fLogin WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void delete(int userId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery(" DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<User> findAllUsersOrderById() {
        Session session = sessionFactory.openSession();
        List<User> query = Collections.emptyList();
        try {
            session.beginTransaction();
            query = session
                    .createQuery("from User", User.class)
                    .getResultList();
            query.sort(Comparator.comparing(User::getId));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return query;
    }

    public Optional<User> findById(int userId) {
        Session session = sessionFactory.openSession();
        Optional<User> query = Optional.empty();
        try {
            session.beginTransaction();
            query = session
                    .createQuery("FROM User AS u WHERE u.id = :fUserId", User.class)
                    .setParameter("fUserId", userId)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return query;
    }

    public List<User> findByLikeLogin(String key) {
        Session session = sessionFactory.openSession();
        List<User> query = Collections.emptyList();
        try {
            session.beginTransaction();
            query = session
                    .createQuery("FROM User AS u WHERE u.login LIKE :fKey", User.class)
                    .setParameter("fKey", String.format("%%%s%%", key))
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return query;
    }

    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.openSession();
        Optional<User> query = Optional.empty();
        try {
            session.beginTransaction();
            query = session
                    .createQuery("FROM User AS u WHERE u.login = :fLogin", User.class)
                    .setParameter("fLogin", login)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return query;
    }

    public void deleteAllUsers() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE from User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
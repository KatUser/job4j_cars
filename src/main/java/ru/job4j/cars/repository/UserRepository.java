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

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
        } finally {
            sessionFactory.getCurrentSession().close();
        }
        return user;
    }

    public void update(User user) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createQuery("UPDATE User SET login = :fLogin WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
        } finally {
            sessionFactory.getCurrentSession().close();
        }
    }

    public void delete(int userId) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createQuery(" DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
        } finally {
            sessionFactory.getCurrentSession().close();
        }
    }

    public List<User> findAllUsersOrderById() {
        List<User> query = Collections.emptyList();
        try {
            Session session = sessionFactory.openSession();
            query = session
                    .createQuery("from User", User.class).list();
            query.sort(Comparator.comparing(User::getId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.getCurrentSession().close();
        }
        return query;
    }

    public Optional<User> findById(int userId) {
        Optional<User> query = Optional.empty();
        try {
            Session session = sessionFactory.openSession();
            query = session
                    .createQuery("FROM User AS u WHERE u.id = :fUserId", User.class)
                    .setParameter("fUserId", userId)
                    .uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.getCurrentSession().close();
        }
        return query;
    }

    public List<User> findByLikeLogin(String key) {
        List<User> query = Collections.emptyList();
        try {
            Session session = sessionFactory.openSession();
            query = session
                    .createQuery("FROM User AS u WHERE u.login LIKE :fKey", User.class)
                    .setParameter("fKey", String.format("%%%s%%", key))
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.getCurrentSession().close();
        }
        return query;
    }

    public Optional<User> findByLogin(String login) {
        Optional<User> query = Optional.empty();
        try {
            Session session = sessionFactory.openSession();
            query = session
                    .createQuery("FROM User AS u WHERE u.login = :fLogin", User.class)
                    .setParameter("fLogin", login)
                    .uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.getCurrentSession().close();
        }
        return query;
    }

    public void deleteAllUsers() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createQuery("DELETE from User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
        } finally {
            sessionFactory.getCurrentSession().close();
        }
    }
}
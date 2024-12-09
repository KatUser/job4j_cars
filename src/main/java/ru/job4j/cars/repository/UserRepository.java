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

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
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

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(Session session, User user) {

        try {
            session.beginTransaction();

            session.createQuery("""
                            UPDATE User
                            SET login = :fLogin
                            WHERE id = :fId"
                            """)

                    .setParameter("fLogin", user.getLogin())

                    .setParameter("fId", user.getId())

                    .executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {

            session.getTransaction().rollback();
        }

    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(Session session, int userId) {

        try {
            session.beginTransaction();

            session.createQuery("""
                            DELETE User
                            WHERE id = :fId
                            """)
                    .setParameter("fId", userId)
                    .executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {

            session.getTransaction().rollback();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllUsersOrderById(Session session) {

        List<User> query = session.createQuery("from User", User.class).list();

        query.sort(Comparator.comparing(User::getId));

        return query;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(Session session, int userId) {

        Query<User> query = session.createQuery(
                "FROM User AS u WHERE u.id = " + userId, User.class
        );

        return Optional.ofNullable(query.uniqueResult());
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(Session session, String key) {

        List<User> query = session
                .createQuery("FROM User AS u WHERE u.login LIKE :fKey", User.class)
                .setParameter("fKey", String.format("%%%s%%", key))
                .list();

        return query;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(Session session, String login) {

        Query<User> query = session.createQuery(
                "FROM User AS u WHERE u.login = :fLogin", User.class
        ).setParameter("fLogin", login);

        return Optional.ofNullable(query.uniqueResult());
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
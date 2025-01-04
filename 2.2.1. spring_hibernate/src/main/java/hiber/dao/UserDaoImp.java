package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private static final String CREATE_TABLE_USERS = """
            CREATE TABLE IF NOT EXISTS `mydbb`.`users` (
                      `id` BIGINT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `last_name` VARCHAR(45) NOT NULL,
                      `email` VARCHAR(45) NOT NULL,
                      PRIMARY KEY (`id`),
                      UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
            ENGINE = InnoDB
            DEFAULT CHARACTER SET = UTF8MB4;
            """;

    private static final String CREATE_TABLE_CAR = """
            CREATE TABLE IF NOT EXISTS `mydbb`.`car` (
                  `user_id` BIGINT NOT NULL,
                  `model` VARCHAR(64) NOT NULL,
                  `series` INT NOT NULL,
                  PRIMARY KEY (`user_id`),
                  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE)
            ENGINE = InnoDB
            DEFAULT CHARACTER SET = UTF8MB4;
            """;

    // создание нужных таблиц
    @Override
    public void createTables() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(CREATE_TABLE_USERS);
        query.executeUpdate();
        query = session.createNativeQuery(CREATE_TABLE_CAR);
        query.executeUpdate();
    }

    // юзеры с одинаковым email добавляться не будут
    @Override
    public void add(User user) {
        List<User> userEmail = null;
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("from User where email = :email", User.class);
        query.setParameter("email", user.getEmail());
        userEmail = query.getResultList();
        if (userEmail == null || userEmail.isEmpty()) {
            sessionFactory.getCurrentSession().save(user);
        }
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }

    //  получение пользователей по машине (модель и серия), могут быть несколько пользователей с одинаковой машиной
    @Override
    public List<User> findUserByCar(String carModel, int carSeries) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery(
                "from User where car.model = :carModel and car.series = :carSeries", User.class);
        query.setParameter("carModel", carModel);
        query.setParameter("carSeries", carSeries);
        return query.getResultList();
    }
}

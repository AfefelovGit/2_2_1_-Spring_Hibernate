package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean addUser(User user) {
        List<User> userEmail = null;

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("from User where email = :email", User.class);
        query.setParameter("email", user.getEmail());
        userEmail = query.getResultList();
        if (userEmail == null || userEmail.isEmpty()) {
            session.save(user);
        }
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }

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

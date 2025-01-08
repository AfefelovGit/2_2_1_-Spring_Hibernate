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

    @Override
    public boolean add(User user) {
        List<User> userEmail = null;

        if (user.getEmail() == null || user.getEmail().length() < 6) {
            return false ;
        }
        if (user.getFirstName() == null || user.getFirstName().length() < 3) {
            return false ;
        }
        if (user.getLastName() == null || user.getLastName().length() < 3) {
            return false ;
        }

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("from User where email = :email", User.class);
        query.setParameter("email", user.getEmail());
        userEmail = query.getResultList();
        if (userEmail == null || userEmail.isEmpty()) {
            sessionFactory.getCurrentSession().save(user);
        }
        return true ;
    }

    @Override
    public List<User> listUsers() {
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

    @Override
    public void removeUserById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
    }

}

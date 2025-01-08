package hiber.dao;

import hiber.model.User;
import java.util.List;

public interface UserDao {
    public boolean add(User user);

    List<User> listUsers();

    public List<User> findUserByCar(String carModel, int carSeries);

    public void removeUserById(Long id);
}

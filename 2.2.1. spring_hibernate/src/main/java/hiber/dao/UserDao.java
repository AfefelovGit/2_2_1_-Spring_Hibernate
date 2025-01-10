package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {
    boolean addUser(User user);

    List<User> getAllUsers();

    List<User> findUserByCar(String carModel, int carSeries);
}

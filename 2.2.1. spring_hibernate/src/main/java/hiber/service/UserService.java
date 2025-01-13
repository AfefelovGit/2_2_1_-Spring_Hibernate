package hiber.service;

import hiber.model.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);

    List<User> getAllUsers();

    List<User> findUserByCar(String carModel, int carSeries);

}

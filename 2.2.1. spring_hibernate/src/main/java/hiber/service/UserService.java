package hiber.service;

import hiber.model.User;
import java.util.List;

public interface UserService {
    void add(User user);

    List<User> listUsers();

    public List<User> findUserByCar(String carModel, int carSeries);

    public void createTables();

}

package hiber.service;

import hiber.model.User;
import java.util.List;

public interface UserService {
    public boolean add(User user);

    List<User> listUsers();

    public List<User> findUserByCar(String carModel, int carSeries);

    public void removeUserById(Long id);

}

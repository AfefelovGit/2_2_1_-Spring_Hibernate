package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

        String modelCar = "Mercedes";
        int seriesCar = 600;
        User user1 = new User("Aleksandr", "Aleksandrov", "Aleksandrov@mail.ru");
        Car car1 = new Car(modelCar, seriesCar);
        user1.setCar(car1);
        userService.add(user1);

        User user2 = new User("Ivan", "Ivanov", "Ivanov@mail.ru");
        Car car2 = new Car("BMW", 7);
        user2.setCar(car2);
        userService.add(user2);

        User user3 = new User("Sergej", "Sergeev", "Sergeev@mail.ru");
        Car car3 = new Car("Tesla", 3);
        user3.setCar(car3);
        userService.add(user3);

        User user4 = new User("Victor", "Victorov", "Victorov@mail.ru");
        Car car4 = new Car("Honda", 10);
        user4.setCar(car4);
        userService.add(user4);

        System.out.println();
        System.out.printf("Users с машиной %s %d\n", modelCar, seriesCar);
        List<User> usersCar = userService.findUserByCar(modelCar, seriesCar);
        if (usersCar != null && !usersCar.isEmpty()) {
            int i = 1;
            for (User user : usersCar) {
                System.out.printf("User%2d: %s\n", i, user);
                i++;
            }
        }

        userService.removeUserById(3L);
        System.out.println();
        List<User> users = userService.listUsers();
        int i = 1;
        for (User user : users) {
            System.out.printf("User%2d: %s\n", i, user);
            i++;
        }

        context.close();
    }
}

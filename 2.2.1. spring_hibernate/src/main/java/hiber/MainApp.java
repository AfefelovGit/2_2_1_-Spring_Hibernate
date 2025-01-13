package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

        String modelCar = "Mercedes";
        int seriesCar = 600;
        userService.addUser(new User("Aleksandr", "Aleksandrov", "Aleksandrov@mail.ru", new Car(modelCar, seriesCar)));
        userService.addUser(new User("Ivan", "Ivanov", "Ivanov@mail.ru", new Car("BMW", 7)));
        userService.addUser(new User("Sergej", "Sergeev", "Sergeev@mail.ru", new Car("Tesla", 3)));
        userService.addUser(new User("Victor", "Victorov", "Victorov@mail.ru", new Car("Honda", 10)));

        System.out.printf("\nUsers with a car %s %d:\n", modelCar, seriesCar);
        List<User> usersCar = userService.findUserByCar(modelCar, seriesCar);
        if (usersCar != null && !usersCar.isEmpty()) {
            int i = 1;
            for (User user : usersCar) {
                System.out.printf("User%2d: %s\n", i, user);
                i++;
            }
        }

        System.out.println("\nAll Users:");
        List<User> users = userService.getAllUsers();
        if (usersCar != null && !usersCar.isEmpty()) {
            int i = 1;
            for (User user : users) {
                System.out.printf("User%2d: %s\n", i, user);
                i++;
            }
        }

        context.close();
    }
}

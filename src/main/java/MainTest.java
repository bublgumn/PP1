import org.exampleProjectOne.model.User;
import org.exampleProjectOne.service.UserService;

import java.util.List;

public class MainTest {
    public static void main(String[] args) throws Exception {
        UserService userService = UserService.getUserService();

        List<User> userList = userService.getAllUser();

        for (User value: userList
             ) {
            System.out.println();
            System.out.println(value.getId() + " " + value.getName() +  " " +value.getPassword() + " " + value.getAge());
        }
    }
}

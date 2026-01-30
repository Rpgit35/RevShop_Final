package service;

import org.junit.*;
import static org.junit.Assert.*;

import model.User;

public class UserServiceTest {

    private UserService userService;

    @Before
    public void setup() {
        userService = new UserService();
    }

    @Test
    public void testRegisterAndLogin() {
        User u = new User();
        u.setName("JUnit User");
        u.setEmail("junit_user@test.com");
        u.setPassword("1234");
        u.setRole("BUYER");
        u.setSecurityQuestion("pet?");
        u.setSecurityAnswer("dog");

        userService.register(u);

        User loggedIn = userService.login(
                "junit_user@test.com",
                "1234"
        );

        assertNotNull(loggedIn);
    }

    @Test
    public void testLoginFail() {
        User user = userService.login("wrong@test.com", "wrong");
        assertNull(user);
    }
}

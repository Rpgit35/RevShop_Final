package service;

import org.junit.*;
import static org.junit.Assert.*;

import model.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserServiceTest {

    private UserService service;
    private final String TEST_EMAIL = "junit_user@test.com";

    @Before
    public void setup() {
        service = new UserService();
        cleanup(); // Clean first

        User u = new User();
        u.setName("JUnit User");
        u.setEmail(TEST_EMAIL);
        u.setPassword("1234");
        u.setRole("BUYER");
        u.setSecurityQuestion("City?");
        u.setSecurityAnswer("Hyd");

        service.registerUser(u);
    }

    @After
    public void cleanup() {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps =
                 con.prepareStatement("DELETE FROM users WHERE email = ?")) {
            ps.setString(1, TEST_EMAIL);
            ps.executeUpdate();
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    @Test
    public void testLoginUser() {
        assertNotNull(service.login(TEST_EMAIL, "1234"));
    }

    @Test
    public void testForgotPassword() {
        boolean result = service.forgotPassword(TEST_EMAIL, "Hyd", "newpass");
        assertTrue(result);
    }
}

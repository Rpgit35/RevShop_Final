package service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserServiceTest.class,
    ProductServiceTest.class,
    OrderServiceTest.class,
    ReviewServiceTest.class,
    FavoriteServiceTest.class
})
public class AllServiceTests {
    // Runs all service test classes
}

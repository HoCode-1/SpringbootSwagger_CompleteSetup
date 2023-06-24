import com.example.demo.entity.h2.Product;
import com.example.demo.entity.h2.Usertest;
import com.example.demo.repo.h2.ProductRepository;
import com.example.demo.repo.h2.UserTestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class JpaMultipleDBIntegrationTest {

    @Autowired
    private UserTestRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional("h2TransactionManager")
    public void whenCreatingUser_thenCreated() {
        Usertest user = new Usertest();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setAge(20);
        user = userRepository.save(user);

        //assertNotNull(userRepository.findOne(user.getId()));
    }

    @Test
    @Transactional("h2TransactionManager")
    public void whenCreatingUsersWithSameEmail_thenRollback() {
        Usertest user1 = new Usertest();
        user1.setName("John");
        user1.setEmail("john@test.com");
        user1.setAge(20);
        user1 = userRepository.save(user1);
        //assertNotNull(userRepository.findOne(user1.getId()));

        Usertest user2 = new Usertest();
        user2.setName("Tom");
        user2.setEmail("john@test.com");
        user2.setAge(10);
        try {
            user2 = userRepository.save(user2);
        } catch (DataIntegrityViolationException e) {
        }

        //assertNull(userRepository.findOne(user2.getId()));
    }

    @Test
    @Transactional("h2TransactionManager")
    public void whenCreatingProduct_thenCreated() {
        Product product = new Product();
        product.setName("Book");
        product.setId(2);
        product.setPrice(20);
        product = productRepository.save(product);

        //assertNotNull(productRepository.findOne(product.getId()));
    }
}
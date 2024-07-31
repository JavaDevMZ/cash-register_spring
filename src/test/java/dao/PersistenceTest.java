package dao;

import com.javadevMZ.CashRegister;
import com.javadevMZ.dao.Product;
import com.javadevMZ.dao.ProductRepository;
import com.javadevMZ.service.Translator;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.LocaleResolver;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = ProductRepository.class)
@EntityScan(basePackageClasses = Product.class)
public class PersistenceTest {

    @Autowired
    private ProductRepository productRepository;
    private static Product product = new Product();

    @BeforeEach
    public void insertTestProduct(){

        productRepository.save(product);
    }
    @Test
    public void setPersists(){
       productRepository.findById(product.getId()).ifPresent(product -> {product.setQuantity(666L);});
       assertEquals(666L, (long)productRepository.findById(product.getId()).orElseThrow().getQuantity());

    }
}

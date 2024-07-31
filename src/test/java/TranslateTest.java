import com.javadevMZ.CashRegister;
import com.javadevMZ.dao.Product;
import com.javadevMZ.service.ProductManager;
import com.javadevMZ.service.Translator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CashRegister.class)
public class TranslateTest {

    @Autowired
    Translator translator;
    @Autowired
    private ProductManager productManager;

    @Test
    public void test() throws Exception {
     String result = new Translator().translate("<h1>Products%20in%20the%20warehouse:%20</br><p><a%20href=/products/4>Phone%20case%20Price:%2025.0%20Quantity:%20988</a></br><p><a%20href=/products/1>Laptop%20Lenovo%20%20Price:%20425.5%20Quantity:%201217</a></br><p><a%20href=/products/5>Charger%20Price:%2035.0%20Quantity:%201000</a></br><p><a%20href=/products/3>Smartphone%20Samsang%20Price:%20200.0%20Quantity:%2083</a></br>".replaceAll("%20", " "),
             Locale.ENGLISH, new Locale("uk"));
        System.out.println(result);
    }

}

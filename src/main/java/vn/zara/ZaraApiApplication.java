package vn.zara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {ZaraApiApplication.class, Jsr310JpaConverters.class})
public class ZaraApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZaraApiApplication.class, args);
    }
}

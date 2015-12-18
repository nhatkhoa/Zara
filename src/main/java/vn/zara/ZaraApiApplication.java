package vn.zara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZaraApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZaraApiApplication.class, args);
    }
}

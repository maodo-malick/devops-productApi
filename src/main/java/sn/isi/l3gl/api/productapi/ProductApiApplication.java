package sn.isi.l3gl.api.productapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "sn.isi.l3gl.api.productapi",
        "sn.isi.l3gl.core.productcore.services"
})
@EntityScan(basePackages = "sn.isi.l3gl.core.productcore.entity")
@EnableJpaRepositories(basePackages = "sn.isi.l3gl.core.productcore.repository")
public class ProductApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApiApplication.class, args);
    }
}
package com.kinan.inventoryservice;

import com.kinan.inventoryservice.models.Product;
import com.kinan.inventoryservice.repositories.IProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
    /*
    @Bean
    public CommandLineRunner commandLineRunner(IProductRepository productRepository){
        return args -> {
            productRepository.saveAll(List.of(
                    Product.builder()
                            .name("Iphone 15 Pro")
                            .description("Apple smartphone")
                            .price(12500D)
                            .quantity(35)
                            .category("smartphone")
                            .build(),
                    Product.builder()
                            .name("Samsung Galaxy s24")
                            .description("Samsung smartphone")
                            .price(11250D)
                            .quantity(28)
                            .category("smartphone")
                            .build(),
                    Product.builder()
                            .name("MacBook Pro")
                            .description("Apple laptop")
                            .price(2000D)
                            .quantity(20)
                            .category("laptop")
                            .build(),
                    Product.builder()
                            .name("Dell XPS 15")
                            .description("Dell laptop")
                            .price(1800D)
                            .quantity(22)
                            .category("laptop")
                            .build(),
                    Product.builder()
                            .name("Google Pixel 6")
                            .description("Google smartphone")
                            .price(900D)
                            .quantity(30)
                            .category("smartphone")
                            .build(),
                    Product.builder()
                            .name("HP Spectre x360")
                            .description("HP laptop")
                            .price(1500D)
                            .quantity(18)
                            .category("laptop")
                            .build(),
                    Product.builder()
                            .name("Sony Xperia 1 III")
                            .description("Sony smartphone")
                            .price(1100D)
                            .quantity(25)
                            .category("smartphone")
                            .build(),
                    Product.builder()
                            .name("Microsoft Surface Laptop 4")
                            .description("Microsoft laptop")
                            .price(1700D)
                            .quantity(20)
                            .category("laptop")
                            .build(),
                    // Ajoutez plus de produits ici
                    Product.builder()
                            .name("Asus ROG Phone 5")
                            .description("Asus smartphone")
                            .price(1000D)
                            .quantity(40)
                            .category("smartphone")
                            .build(),
                    Product.builder()
                            .name("Lenovo ThinkPad X1 Carbon")
                            .description("Lenovo laptop")
                            .price(2300D)
                            .quantity(15)
                            .category("laptop")
                            .build(),
                    Product.builder()
                            .name("OnePlus 9 Pro")
                            .description("OnePlus smartphone")
                            .price(1050D)
                            .quantity(32)
                            .category("smartphone")
                            .build(),
                    Product.builder()
                            .name("Acer Swift 3")
                            .description("Acer laptop")
                            .price(1200D)
                            .quantity(24)
                            .category("laptop")
                            .build()
            ));
        };

    }
    */
}

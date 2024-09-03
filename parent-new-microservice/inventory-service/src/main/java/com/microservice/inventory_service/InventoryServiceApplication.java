package com.microservice.inventory_service;

import com.microservice.inventory_service.entity.InventoryEntity;
import com.microservice.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean //launch bean when application start-up
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			InventoryEntity inventory1 = new InventoryEntity();
			inventory1.setSkuCode("rog_z_g14");
			inventory1.setQuantity(10);

			InventoryEntity inventory2 = new InventoryEntity();
			inventory2.setSkuCode("rz_b_14");
			inventory2.setQuantity(10);

			InventoryEntity inventory3 = new InventoryEntity();
			inventory3.setSkuCode("hp_o_t_14");
			inventory3.setQuantity(10);

			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
			inventoryRepository.save(inventory3);
		};
	}
}

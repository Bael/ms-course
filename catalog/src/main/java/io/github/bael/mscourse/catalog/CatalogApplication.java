package io.github.bael.mscourse.catalog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;

@EnableCaching
@SpringBootApplication
@Slf4j
public class CatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogApplication.class, args);
	}

	@PostConstruct
	public void onInit() {
		String catalog_app_use_cache = System.getenv("CATALOG_APP_USE_CACHE");
		log.info("CATALOG_APP_USE_CACHE is {}", catalog_app_use_cache);

	}

}

package br.com.j38.poi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan(basePackages = {"br.com.j38.poi.model"})
@EnableMongoRepositories(basePackages = {"br.com.j38.poi.repository"})
@ComponentScan(basePackages = {"br.com.j38.poi.service", "br.com.j38.poi.controller", "br.com.j38.poi.exception"})
@SpringBootApplication
@EnableSwagger2
public class Mobi7PoiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Mobi7PoiApiApplication.class, args);
    }

}

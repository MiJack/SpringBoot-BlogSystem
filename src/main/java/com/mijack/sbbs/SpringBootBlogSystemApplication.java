package com.mijack.sbbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Mr.Yuan
 */
@SpringBootApplication
@ComponentScan("com.mijack.sbbs.*")
public class SpringBootBlogSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBlogSystemApplication.class, args);
    }
}

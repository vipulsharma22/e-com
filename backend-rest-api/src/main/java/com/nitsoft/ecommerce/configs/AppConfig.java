package com.nitsoft.ecommerce.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:/application-dev.properties")
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    public String getValueOfKey(String key) {
        return "env.getProperty(key)";
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000", "https://lil-munchkin.herokuapp.com").allowedMethods("*").allowCredentials(true);
    }

}

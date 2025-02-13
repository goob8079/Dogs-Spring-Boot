package com.justice.dogs.login;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// ViewControllerRegistry is mainly used to return a view (HTML page, etc.) without processing any data.
// This makes it so a controller is not required to view HTML pages and such. 

// I will keep this MvcConfig class with the ViewControllerRegistry and ResourceHandlerRegistry just in case
// even though I have a controller.

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home/dogslist").setViewName("dogs-list");
        registry.addViewController("/home/dogstypes").setViewName("dog-types");
        registry.addViewController("/home/pibbletypes").setViewName("pibble-types");
    } 
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}

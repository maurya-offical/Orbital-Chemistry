package com.orbitalchemistry.config;

import com.orbitalchemistry.service.StorageService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Serves uploaded images at /uploads/<name>. */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StorageService storage;
    public WebConfig(StorageService storage) { this.storage = storage; }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(storage.dir().toUri().toString())
                .setCachePeriod(3600);
    }
}

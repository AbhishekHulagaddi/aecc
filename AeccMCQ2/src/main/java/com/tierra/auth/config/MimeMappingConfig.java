package com.tierra.auth.config;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MimeMappingConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> customizer() {
        return factory -> {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            mappings.add("mjs", "application/javascript"); // ✅ Fix MIME type
            mappings.add("js", "application/javascript");  // Ensure .js also correct
            factory.setMimeMappings(mappings);
        };
    }
}

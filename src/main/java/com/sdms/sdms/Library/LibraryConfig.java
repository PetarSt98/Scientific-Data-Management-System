package com.sdms.sdms.Library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryConfig {

    @Bean
    public BookFormatter bookFormatter() {
        return new BookFormatter();
    }
}

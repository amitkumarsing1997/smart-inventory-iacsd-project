package com.iacsd.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public freemarker.template.Configuration getConfiguration()
    {
        freemarker.template.Configuration cfg= new freemarker.template.Configuration();
        cfg.setClassForTemplateLoading(AppConfig.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

//    @Bean
//        public freemarker.template.Configuration getConfiguration()
//    {
//             return new freemarker.template.Configuration();
//    }
//    var cfg = new freemarker.template.Configuration();
//
//        cfg.setClassForTemplateLoading(FreeMarkerConsoleEx.class, "/views");
//        cfg.setDefaultEncoding("UTF-8");
//
}

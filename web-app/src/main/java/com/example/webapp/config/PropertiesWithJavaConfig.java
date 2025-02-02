package com.example.webapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class PropertiesWithJavaConfig {

    private final Environment env;

    public String getDbUrl() {
        return env.getProperty("db.url");
    }

    public String getUsername() {
        return env.getProperty("db.username");
    }

    public String getPassword() {
        return env.getProperty("db.password");
    }

    public String getDriver() {
        return env.getProperty("db.driver");
    }

    public String getHibernateDialect() {
        return env.getProperty("hibernate.dialect");
    }

    public String getHibernateLazyNoTrans() {
        return env.getProperty("hibernate.enable_lazy_load_no_trans");
    }

    public String getHibernateShowSql() {
        return env.getProperty("hibernate.show_sql");
    }

    public String getHibernateDdlAuto() {
        return env.getProperty("hibernate.hbm2ddl.auto");
    }

}

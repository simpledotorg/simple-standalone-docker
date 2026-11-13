package org.simple.keycloak;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleUserStorageProviderFactory implements UserStorageProviderFactory<SimpleUserStorageProvider> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleUserStorageProviderFactory.class);
    public static final String ID = "simple-legacy-db";
    private static final DataSource SIMPLE_DB_DATASOURCE = getDataSource();

    @Override
    public SimpleUserStorageProvider create(KeycloakSession ks, ComponentModel cm) {
        return new SimpleUserStorageProvider(ks, cm, SIMPLE_DB_DATASOURCE);
    }

    @Override
    public String getId() {
        return ID;
    }

    private static DataSource getDataSource() {
        String rawUrl = System.getenv().getOrDefault(
                "LEGACY_DB_URL",
                "jdbc:postgresql://postgres:5432/simple-server_test?options=-csearch_path%3Dkeycloak"
        );
        // Strip embedded "user:password@" if present in the URL
        String cleanJdbcUrl = rawUrl.replaceFirst("://[^/@]+@", "://");

        String dbUser = System.getenv().getOrDefault("LEGACY_DB_USER", "postgres");
        String dbPass = System.getenv().getOrDefault("LEGACY_DB_PASSWORD", "password");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(cleanJdbcUrl);
        hikariConfig.setUsername(dbUser);
        hikariConfig.setPassword(dbPass);
        hikariConfig.setDriverClassName("org.postgresql.Driver");

        // Sensible pool settings for authentication queries
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTimeout(5000);
        hikariConfig.setPoolName("LegacyAuthPool");

        return new HikariDataSource(hikariConfig);
    }
}

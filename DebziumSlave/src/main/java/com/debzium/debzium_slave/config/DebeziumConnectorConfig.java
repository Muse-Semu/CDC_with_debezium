package com.debzium.debzium_slave.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;

@Configuration
public class DebeziumConnectorConfig {

    @Bean
    public io.debezium.config.Configuration customerConnector(Environment env) throws Exception {
        // Use properties from application.yml
        return io.debezium.config.Configuration.create()
                .with("name", env.getProperty("debezium.connector.name"))
                .with("connector.class", env.getProperty("debezium.connector.connector-class"))
                .with("offset.storage", env.getProperty("debezium.connector.offset-storage"))
                .with("offset.storage.file.filename", env.getProperty("debezium.connector.offset-storage-file-filename"))
                .with("offset.flush.interval.ms", env.getProperty("debezium.connector.offset-flush-interval-ms"))
                .with("database.hostname", env.getProperty("debezium.connector.database.hostname"))
                .with("database.port", env.getProperty("debezium.connector.database.port"))
                .with("database.user", env.getProperty("debezium.connector.database.user"))
                .with("database.password", env.getProperty("debezium.connector.database.password"))
                .with("database.dbname", env.getProperty("debezium.connector.database.dbname"))
                .with("database.server.id", env.getProperty("debezium.connector.database.server-id"))
                .with("database.server.name", env.getProperty("debezium.connector.database.server-name"))
                .with("topic.prefix", env.getProperty("debezium.connector.topic-prefix"))
                .with("database.history", env.getProperty("debezium.connector.database-history"))
                .with("publication.autocreate.mode", env.getProperty("debezium.connector.publication-autocreate-mode"))
                .with("plugin.name", env.getProperty("debezium.connector.plugin-name"))
                .with("slot.name", env.getProperty("debezium.connector.slot-name"))
                .with("table.include.list", env.getProperty("debezium.connector.table-include-list"))
                .with("include.metadata", env.getProperty("debezium.include-metadata"))
                .with("include.schema.changes", env.getProperty("debezium.include-schema-changes"))
                .build();
    }
}

package com.cuizhanming.multidatasource.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.cuizhanming.multidatasource.gcp.repository.mysql")
@EnableCassandraRepositories(basePackages = "com.cuizhanming.multidatasource.gcp.repository.cassandra")
public class DataSourceConfig {
}

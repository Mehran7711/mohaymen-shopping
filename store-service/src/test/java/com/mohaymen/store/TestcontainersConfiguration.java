package com.mohaymen.store;

import java.time.Duration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public  class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    OracleContainer oracleContainer() {
        DockerImageName image = DockerImageName.parse("docker.arvancloud.ir/gvenzl/oracle-xe")
                .asCompatibleSubstituteFor("gvenzl/oracle-free");

        OracleContainer container = new OracleContainer(image).withStartupTimeout(Duration.ofMinutes(5));

        container.start();

        System.out.println("JDBC URL  : " + container.getJdbcUrl());
        System.out.println("Username  : " + container.getUsername());
        System.out.println("Password  : " + container.getPassword());

        return container;
    }
}

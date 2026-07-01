package com.mohaymen.store;

import java.time.Duration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
@SuppressWarnings("resource")
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    OracleContainer oracleContainer() {
        DockerImageName image = DockerImageName.parse("docker.arvancloud.ir/gvenzl/oracle-xe")
                .asCompatibleSubstituteFor("gvenzl/oracle-free");

        return new OracleContainer(image).withStartupTimeout(Duration.ofMinutes(5));
    }
}

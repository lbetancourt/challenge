package com.mercadolibre.challengue;

import com.mercadolibre.challengue.ip.domain.IPInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withPassword("inmemory")
            .withUsername("inmemory");

    static {
        GenericContainer redis = new GenericContainer("redis:3-alpine")
                .withExposedPorts(6379);
        redis.start();

        System.setProperty("spring.redis.host", redis.getContainerIpAddress());
        System.setProperty("spring.redis.port", redis.getFirstMappedPort() + "");
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    void givenIPWhenSendIPThenReturnIPInfo() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/ip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\": \"5.6.7.8\"}")
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ipForbidden.ip").value("5.6.7.8"));
    }

    @Test
    void givenIPBannedWhenPostIPBannedThenReturnIPBanned() throws Exception {
        var ipInfo = IPInfo.buildFrom(
                "Colombia",
                "CO",
                "COP",
                "4507.388145"
        );

        ResultActions resultActions = mockMvc.perform(get("/api/v1/ip/186.28.158.0"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ipInfo.nameCountry").value(ipInfo.getNameCountry()))
                .andExpect(jsonPath("$.ipInfo.isoCountry").value(ipInfo.getIsoCountry()))
                .andExpect(jsonPath("$.ipInfo.currency").value(ipInfo.getCurrency()))
                .andExpect(jsonPath("$.ipInfo.exchangePrice").value(ipInfo.getExchangePrice()));
    }

}

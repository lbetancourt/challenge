package com.mercadolibre.challengue.ip.infrastructure.controller;

import com.mercadolibre.challengue.ip.application.IPProcessCommand;
import com.mercadolibre.challengue.ip.application.IPProcessQuery;
import com.mercadolibre.challengue.ip.domain.IPForbidden;
import com.mercadolibre.challengue.ip.domain.IPForbiddenException;
import com.mercadolibre.challengue.ip.domain.IPInfo;
import com.mercadolibre.challengue.ip.infrastructure.IPBanned;
import com.mercadolibre.challengue.ip.infrastructure.IPProcess;
import com.mercadolibre.challengue.shared.config.IdentifierCreator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(IPController.class)
public class IPControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPProcess ipProcess;
    @MockBean
    private IPBanned ipBanned;

    @Test
    public void givenIPWhenSendGetIpInfoThenGetIPInfoAggregate() throws Exception {
        var ipInfo = IPInfo.buildFrom(
                "Colombia",
                "CO",
                "COP",
                "4503.799995"
        );

        var ipLocationResponseDTO = IPLocationResponseDTO.buildFrom(
                IdentifierCreator.generateUUID(),
                ipInfo
        );

        Mockito
                .when(ipProcess.getIPInfo(any(IPProcessQuery.class)))
                .thenReturn(ipLocationResponseDTO);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/ip/186.28.158.0"));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.requestIdentifier").value(ipLocationResponseDTO.getRequestIdentifier().toString()))
                .andExpect(jsonPath("$.ipInfo.nameCountry").value(ipInfo.getNameCountry()))
                .andExpect(jsonPath("$.ipInfo.isoCountry").value(ipInfo.getIsoCountry()))
                .andExpect(jsonPath("$.ipInfo.currency").value(ipInfo.getCurrency()))
                .andExpect(jsonPath("$.ipInfo.exchangePrice").value(ipInfo.getExchangePrice()));
    }

    @Test
    public void givenIPBannedWhenSendGetIpInfoThenThrowIPForbidden() throws Exception {
        Mockito
                .when(ipProcess.getIPInfo(any(IPProcessQuery.class)))
                .thenThrow(new IPForbiddenException(String.format("IP %s is Banned", "5.6.7.8")));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/ip/5.6.7.8"));

        resultActions
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void givenIPBannedWhenSendGetIpInfoThenThrowUnKnow() throws Exception {
        Mockito
                .when(ipProcess.getIPInfo(any(IPProcessQuery.class)))
                .thenThrow(new RuntimeException("Error Use case"));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/ip/5.6.7.8"));

        resultActions
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void givenIPBannedWhenSendPostSetIPBannedThenSaveIPBanned() throws Exception {
        IPForbidden ipForbidden = IPForbidden.buildFrom("5.6.7.8");

        IPBannedResponseDTO ipBannedResponseDTO = IPBannedResponseDTO.builder()
                .withRequestIdentifier(IdentifierCreator.generateUUID())
                .withIpForbidden(ipForbidden)
                .build();

        Mockito
                .when(ipBanned.recordIPAsBanned(any(IPProcessCommand.class)))
                .thenReturn(ipBannedResponseDTO);

        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/ip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\": \"5.6.7.8\"}")
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.requestIdentifier").value(ipBannedResponseDTO.getRequestIdentifier().toString()))
                .andExpect(jsonPath("$.ipForbidden.ip").value(ipForbidden.getIp()));
    }

    @Test
    public void givenIPBannedWhenSendPostSetIPBannedThenThrowUnKnow() throws Exception {
        Mockito
                .when(ipBanned.recordIPAsBanned(any(IPProcessCommand.class)))
                .thenThrow(new RuntimeException("Error Use case"));

        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/ip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ip\": \"5.6.7.8\"}")
        );

        resultActions
                .andExpect(status().isInternalServerError());
    }
}

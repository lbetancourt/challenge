package com.mercadolibre.challengue.shared.iptocountry;

import com.mercadolibre.challengue.shared.IPToCountry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IPToCountryTest {
    @Autowired
    private IPToCountry ipToCountry;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void givenIPWhenCallAPIIPToCountryThenGetCountryCode() {
        IPToCountryResponseDTO ipToCountryResponseDTO = new IPToCountryResponseDTO();
        ipToCountryResponseDTO.setCountryCode("CO");
        ipToCountryResponseDTO.setCountryCode3("COL");
        ipToCountryResponseDTO.setCountryName("Colombia");
        ipToCountryResponseDTO.setCountryEmoji("\uD83C\uDDE8\uD83C\uDDF4");

        Mockito
                .when(restTemplate.getForObject(anyString(), eq(IPToCountryResponseDTO.class)))
                .thenReturn(ipToCountryResponseDTO);

        IPToCountryResponseDTO expectedIpToCountryResponseDTO = ipToCountry.getCountryFrom("5.6.7.8");

        assertAll("IPToCountryResponseDTO",
                () -> assertEquals(expectedIpToCountryResponseDTO.getCountryCode(), ipToCountryResponseDTO.getCountryCode()),
                () -> assertEquals(expectedIpToCountryResponseDTO.getCountryCode3(), ipToCountryResponseDTO.getCountryCode3()),
                () -> assertEquals(expectedIpToCountryResponseDTO.getCountryName(), ipToCountryResponseDTO.getCountryName())
        );
    }

    @Test
    public void givenIPWhenCallAPIIPToCountryThenException() {
        Mockito
                .when(restTemplate.getForObject(anyString(), eq(IPToCountryResponseDTO.class)))
                .thenThrow(new RestClientException("no GET"));
        assertThrows(RestClientException.class, () -> ipToCountry.getCountryFrom("5.6.7.8"));
    }
}

package com.mercadolibre.challengue.shared.fixer;

import com.mercadolibre.challengue.shared.Fixer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FixerTest {

    @Autowired
    private Fixer fixer;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void givenNeedItExchangeCurrenciesWhenCallAPIFixerThenResponseCurrenciesList() {
        Map<String, Object> rates = new HashMap<>();
        rates.put("COP", 4503.799995);
        rates.put("CRC", 731.369999);
        rates.put("MDL", 21.2264);

        FixerResponseDTO expectedFixerResponseDTO = new FixerResponseDTO();
        expectedFixerResponseDTO.setSuccess(true);
        expectedFixerResponseDTO.setTimestamp(1626539525L);
        expectedFixerResponseDTO.setBase("EUR");
        expectedFixerResponseDTO.setDate("2021-07-17");
        expectedFixerResponseDTO.setRates(rates);

        Mockito
                .when(restTemplate.getForObject(any(URI.class), eq(FixerResponseDTO.class)))
                .thenReturn(expectedFixerResponseDTO);
        FixerResponseDTO fixerResponseDTO = fixer.getForeignExchangeFrom();

        assertAll("FixerResponseDTO",
                () -> assertEquals(expectedFixerResponseDTO.getTimestamp(), fixerResponseDTO.getTimestamp()),
                () -> assertEquals(expectedFixerResponseDTO.getDate(), fixerResponseDTO.getDate()),
                () -> assertEquals(expectedFixerResponseDTO.getBase(), fixerResponseDTO.getBase()),
                () -> assertEquals(3, fixerResponseDTO.getRates().size())
        );
    }

    @Test
    public void givenNeedItExchangeCurrenciesWhenCallAPIFixerThenException() {
        Mockito
                .when(restTemplate.getForObject(any(URI.class), eq(FixerResponseDTO.class)))
                .thenThrow(new RestClientException("no GET"));

        assertThrows(RestClientException.class, () -> fixer.getForeignExchangeFrom());
    }
}
